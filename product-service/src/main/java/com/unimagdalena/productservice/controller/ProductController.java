package com.unimagdalena.productservice.controller;

import com.unimagdalena.productservice.entity.Product;
import com.unimagdalena.productservice.service.ProductService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService) {

        this.productService = productService;
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
    @CircuitBreaker(name = "createProducToInventory" , fallbackMethod = "fallbackCreateProduct")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> createProduct(@RequestBody Product product) {
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


    private Mono<Product> fallbackCreateProduct(Product product) {

        Product error = new Product("503","Servicio no disponible",new BigDecimal("503.0"),"");

        return Mono.defer(() -> Mono.justOrEmpty(error))
                .subscribeOn(Schedulers.boundedElastic());
    }


}
