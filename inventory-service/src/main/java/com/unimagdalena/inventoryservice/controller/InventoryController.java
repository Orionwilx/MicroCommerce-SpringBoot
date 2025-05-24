package com.unimagdalena.inventoryservice.controller;

import com.unimagdalena.inventoryservice.dto.InventoryUpdateRequest;
import com.unimagdalena.inventoryservice.dto.InventoryCreateRequest;
import com.unimagdalena.inventoryservice.entity.Inventory;
import com.unimagdalena.inventoryservice.service.InventoryService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public Mono<ResponseEntity<Flux<Inventory>>> getAllInventoryItems() {
        return Mono.just(ResponseEntity.ok(inventoryService.getAllInventoryItems()));
    }


    @GetMapping("/{id}")
    @CircuitBreaker(name = "inventorygetById", fallbackMethod = "fallback")
    public Mono<ResponseEntity<Inventory>> getInventoryItemById(@PathVariable String id) {
        if (id.equals("999")) {
            throw new IllegalArgumentException("Simulated failure for ID: " + id);

        }
        return inventoryService.getInventoryItemById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Inventory> createInventoryItem(@RequestBody InventoryCreateRequest request) {
        Inventory inventory = new Inventory();
        inventory.setId(request.getProductId());
        inventory.setProductName(request.getProductName());
        inventory.setQuantity(request.getQuantity());
        return inventoryService.createInventoryItem(inventory);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Inventory>> updateInventoryItem(@PathVariable String id, @RequestBody Inventory inventory) {
        return inventoryService.updateInventoryItem(id, inventory)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteInventoryItem(@PathVariable String id) {
        return inventoryService.deleteInventoryItem(id);
    }

    @GetMapping("/product/{productName}")
    public Mono<ResponseEntity<Inventory>> getInventoryByProductName(@PathVariable String productName) {
        return inventoryService.getInventoryByProductName(productName)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/product/update")
    public Mono<Inventory> updateInventoryQuantity(@RequestBody InventoryUpdateRequest request) {
        return inventoryService.decreaseInventory(request.getProductName(), request.getQuantityToDecrease());
    }

    String fallback(String id, Throwable t) {
        return "Fallback: Could not retrieve item " + id + ". Please try again later.";
    }
}
