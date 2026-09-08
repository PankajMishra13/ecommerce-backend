package ecommerce_backend.controller;

import ecommerce_backend.dto.OrderRequestDto;
import ecommerce_backend.dto.OrderResponseDto;
import ecommerce_backend.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> placeOrder(
            @Valid @RequestBody OrderRequestDto request) {

        OrderResponseDto response =
                orderService.placeOrder(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getMyOrders() {

        List<OrderResponseDto> response =
                orderService.getMyOrders();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(
            @PathVariable Long orderId) {

        OrderResponseDto response =
                orderService.getOrderById(orderId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(
            @PathVariable Long orderId) {

        orderService.cancelOrder(orderId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Void> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {

        orderService.updateOrderStatus(orderId, status);

        return ResponseEntity.ok().build();
    }
}