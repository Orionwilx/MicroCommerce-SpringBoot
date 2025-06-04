package com.unimagdalena.productservice.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryCreateRequest {

    private String productId;
    private String productName;
    private Integer quantity;

}

