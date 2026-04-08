package com.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final WebClient webClient;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest req) {

        log.info("Placing order for product {}", req.productCode());

        InventoryResponse inventory =
                webClient.get()
                        .uri("/api/inventory/{code}", req.productCode())
                        .retrieve()
                        .bodyToMono(InventoryResponse.class)
                        .block();

        if (inventory != null && inventory.available()) {
            log.info("Order accepted");
            return ResponseEntity.ok("ORDER_ACCEPTED");
        }

        log.warn("Order rejected - out of stock");
        return ResponseEntity.badRequest().body("OUT_OF_STOCK");
    }

	public record OrderRequest(String productCode) {}
	public record InventoryResponse(
			String productCode,
			boolean available,
			int quantity
	) {}
}
