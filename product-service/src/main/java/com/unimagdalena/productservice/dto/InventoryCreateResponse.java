package com.unimagdalena.productservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryCreateResponse {
    private String id;
    private String productName;
    private Integer quantity;
    private boolean success;
    private String message;

}
