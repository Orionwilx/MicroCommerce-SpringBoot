package com.unimagdalena.orderservice.controller;

import com.unimagdalena.orderservice.entity.Order;
import com.unimagdalena.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
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
    public Mono<Order> fallbackCreateOrderToInvetory(Order order) {
        Order error = new Order("503","en la comunicacion con el microservicio Inventario",503, new BigDecimal("503"), LocalDateTime.now());

        return Mono.just(error).subscribeOn(Schedulers.boundedElastic());
    }


}
