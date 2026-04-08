package com.inventory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @GetMapping("/{productCode}")
    public InventoryResponse checkStock(@PathVariable String productCode) {
        // demo hardcode
        boolean available = !"OUT".equals(productCode);

        return new InventoryResponse(
                productCode,
                available,
                available ? 10 : 0
        );
    }
}
