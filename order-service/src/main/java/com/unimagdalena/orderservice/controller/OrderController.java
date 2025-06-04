package com.unimagdalena.orderservice.controller;

import com.unimagdalena.orderservice.entity.Order;
import com.unimagdalena.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public Flux<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Order>> getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PostMapping
    @CircuitBreaker(name = "createOrderToInvetory", fallbackMethod = "fallbackCreateOrderToInvetory")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Order> createOrder(@RequestBody Order order) {

        return orderService.createOrder(order);
    }

    @PutMapping("/{id}")
    public Mono<Order> updateOrder(@PathVariable String id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteOrder(@PathVariable String id){
        return orderService.deleteOrder(id);
    }


    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Mono<Order> fallbackCreateOrderToInvetory(Order order, Exception ex) {

        String errorMessage = ex != null ? ex.getMessage() : "Error desconocido";
        log.warn("Circuit breaker activated for createOrderToInvetory. Error: {}", errorMessage);

        // Crear una orden con estado degradado
        Order fallbackOrder = new Order();
        fallbackOrder.setId("PENDING_INVENTORY_UPDATE");
        fallbackOrder.setProductName("Servicio de inventario no disponible, intente mas tarde.");

        return Mono.just(fallbackOrder);
    }


}
