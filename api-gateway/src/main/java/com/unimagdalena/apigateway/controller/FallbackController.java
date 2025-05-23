package com.unimagdalena.apigateway.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback-response")
public class FallbackController {

    @GetMapping("/products-unavailable")
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Mono<String> productsFallback() {

        return Mono.just("El servicio de producto no está disponible en este momento. Por favor, inténte de nuevo más tarde.");
    }

    @GetMapping("/inventory-unavailable")
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Mono<String> inventoryFallback() {
        return Mono.just("El servicio de inventarios no está disponible en este momento. Por favor, inténte de nuevo más tarde.");
    }

    @GetMapping("/orders-unavailable")
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Mono<String> ordersFallback() {
        return Mono.just("El servicio de pedidos no está disponible en este momento. Por favor, inténte de nuevo más tarde.");
    }

    @GetMapping("/payments-unavailable")
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Mono<String> paymentsFallback() {
        return Mono.just("El servicio de pagos no está disponible en este momento. Por favor, inténte de nuevo más tarde.");
    }

}

