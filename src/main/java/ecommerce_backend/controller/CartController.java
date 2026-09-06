package ecommerce_backend.controller;

import ecommerce_backend.dto.CartItemRequestDto;
import ecommerce_backend.dto.CartResponseDto;
import ecommerce_backend.service.CartService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartResponseDto> getMyCart() {

        CartResponseDto response = cartService.getMyCart();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponseDto> addItemToCart(
            @Valid @RequestBody CartItemRequestDto request) {

        CartResponseDto response = cartService.addItemToCart(request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<CartResponseDto> updateCartItem(
            @PathVariable Long cartItemId,
            @RequestParam @Min(1) Integer quantity) {

        CartResponseDto response =
                cartService.updateCartItem(cartItemId, quantity);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(
            @PathVariable Long cartItemId) {

        cartService.removeCartItem(cartItemId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart() {

        cartService.clearCart();

        return ResponseEntity.noContent().build();
    }
}