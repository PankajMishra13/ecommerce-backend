package ecommerce_backend.controller;

import ecommerce_backend.dto.WishlistResponseDto;
import ecommerce_backend.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping
    public WishlistResponseDto getMyWishlist() {
        return wishlistService.getMyWishlist();
    }

    @PostMapping("/{productId}")
    public WishlistResponseDto addItem(@PathVariable Long productId) {
        return wishlistService.addItem(productId);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long productId) {
        wishlistService.removeItem(productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearWishlist() {
        wishlistService.clearWishlist();
        return ResponseEntity.noContent().build();
    }
}