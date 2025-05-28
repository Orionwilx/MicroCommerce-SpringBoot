package com.unimagdalena.productservice.controller;

import com.unimagdalena.productservice.entity.Product;
import com.unimagdalena.productservice.service.ProductService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
public class ProductController {


    final CircuitBreaker circuitBreaker;

    private final ProductService productService;

    public ProductController(ProductService productService, CircuitBreaker registry) {

        this.productService = productService;
        circuitBreaker = registry.circuitBreaker("createProducToInventory");
    }


    @GetMapping
    public Flux<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Mono<Product> getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "createProducToInventory" , fallbackMethod = "fallbackCreateProduct")
    public Mono<Product> createProduct(@RequestBody Product product) {
        if (product.getName().equals("999")) {
            throw new IllegalArgumentException("Simulated failure for name: " + product.getName());

        }
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public Mono<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id);
    }


    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Mono<Product> fallbackCreateProduct(Product product) {

        Product error = new Product("503","Servicio no disponible",new BigDecimal("503.0"),"");

        return Mono.defer(() -> Mono.justOrEmpty(error))
                .subscribeOn(Schedulers.boundedElastic());
    }

}
