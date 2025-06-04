package com.unimagdalena.productservice.service;

import com.unimagdalena.productservice.dto.InventoryCreateResponse;
import com.unimagdalena.productservice.dto.ProductResponse;
import com.unimagdalena.productservice.entity.Product;
import com.unimagdalena.productservice.repository.ProductRepository;
import com.unimagdalena.productservice.dto.InventoryCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.util.UUID;

@Service

@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final InventoryClient inventoryClient;



    public Flux<Product> getAllProducts() {
        return Flux.defer(() -> Flux.fromIterable(productRepository.findAll()))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Product> getProductById(String id) {
        return Mono.defer(() -> Mono.justOrEmpty(productRepository.findById(id)))
                .subscribeOn(Schedulers.boundedElastic());
    }




    public Mono<ProductResponse> createProduct(Product product) {
        log.info("Iniciando creación de producto: {}", product.getName());

        if (product.getName() == null || product.getName().isEmpty()) {
            return Mono.error(new IllegalArgumentException("Product name cannot be empty"));
        }

        product.setId(UUID.randomUUID().toString());


        return Mono.fromCallable(() -> productRepository.save(product))
                .subscribeOn(Schedulers.boundedElastic()) // Para operaciones bloqueantes
                .doOnNext(savedProduct -> log.info("Producto guardado con ID: {}", savedProduct.getId()))
                .flatMap(this::createInventoryForProduct)
                .onErrorResume(error -> {
                    log.error("Error al crear producto: {}", error.getMessage());
                    return Mono.error(new RuntimeException("Error al crear producto: " + error.getMessage()));
                });

    }


    public Mono<Product> updateProduct(String id, Product product) {
        return Mono.defer(() -> {
            if (productRepository.existsById(id)) {
                product.setId(id);
                return Mono.just(productRepository.save(product));
            }
            return Mono.empty();
        }).subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Void> deleteProduct(String id) {
        return Mono.defer(() -> {
            productRepository.deleteById(id);
            return Mono.empty();
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }






    private Mono<ProductResponse> createInventoryForProduct(Product savedProduct) {
        // Crear el request para inventario
        InventoryCreateRequest inventoryRequest = InventoryCreateRequest.builder()
                .productId(savedProduct.getId())
                .productName(savedProduct.getName())
                .quantity(0)
                .build();

        return inventoryClient.createInventoryItem(inventoryRequest)
                .map(inventoryResponse -> buildSuccessResponse(savedProduct, inventoryResponse))
                .onErrorResume(error -> {
                    log.error("Error al crear inventario para producto {}: {}",
                            savedProduct.getName(), error.getMessage());
                    return Mono.just(buildErrorResponse(savedProduct, error));
                });
    }

    private ProductResponse buildSuccessResponse(Product product, InventoryCreateResponse inventoryResponse) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .inventoryCreated(inventoryResponse.isSuccess())
                .message(inventoryResponse.isSuccess()
                        ? "Producto e inventario creados exitosamente"
                        : "Producto creado, pero hubo problemas con el inventario: " + inventoryResponse.getMessage())
                .build();
    }

    private ProductResponse buildErrorResponse(Product product, Throwable error) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .inventoryCreated(false)
                .message("Producto creado, pero no se pudo crear el inventario: " + error.getMessage())
                .build();
    }



}