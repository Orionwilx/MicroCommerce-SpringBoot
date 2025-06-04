package com.unimagdalena.productservice.service;


import com.unimagdalena.productservice.dto.InventoryCreateRequest;
import com.unimagdalena.productservice.dto.InventoryCreateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryClient {

    private final WebClient.Builder webClientBuilder;

    private String inventoryServiceUrl ="http://inventory-service";

    @CircuitBreaker(name = "inventory-service", fallbackMethod = "createInventoryFallback")
    //@Retry(name = "inventory-service")
    //@TimeLimiter(name = "inventory-service")
    public Mono<InventoryCreateResponse> createInventoryItem(InventoryCreateRequest inventoryRequest) {
        log.info("Creando item en inventario para producto: {}", inventoryRequest.getProductName());

        return webClientBuilder.build()
                .post()
                .uri(inventoryServiceUrl + "/api/inventory")
                .bodyValue(inventoryRequest)
                .retrieve()
                .bodyToMono(InventoryCreateResponse.class)
                .map(response -> {
                    response.setSuccess(true);
                    log.info("Item creado exitosamente en inventario y estado actualizado: {}", response);
                    return response;
                })
                .doOnError(error -> log.error("Error al crear item en inventario: {}", error.getMessage()));
    }

    // Método fallback para cuando falla el circuit breaker
    public Mono<InventoryCreateResponse> createInventoryFallback(InventoryCreateRequest inventoryRequest, Exception ex) {
        log.error("Circuit breaker activado para inventory-service. Error: {}", ex.getMessage());

        return Mono.just(InventoryCreateResponse.builder()
                .id("fallback-" + System.currentTimeMillis())
                .productName(inventoryRequest.getProductName())
                .quantity(0)
                .success(false)
                .message("Servicio de inventario no disponible. Se creará el producto sin inventario.")
                .build());
    }

}
