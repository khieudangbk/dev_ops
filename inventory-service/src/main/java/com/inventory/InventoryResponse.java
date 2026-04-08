package com.inventory;

public record InventoryResponse(
        String productCode,
        boolean available,
        int quantity
) {}
