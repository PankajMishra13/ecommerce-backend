package ecommerce_backend.controller;

import ecommerce_backend.dto.CouponRequestDto;
import ecommerce_backend.dto.CouponResponseDto;
import ecommerce_backend.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<CouponResponseDto> createCoupon(
            @Valid @RequestBody CouponRequestDto request) {

        CouponResponseDto response =
                couponService.createCoupon(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<CouponResponseDto>> getAllCoupons() {

        List<CouponResponseDto> response =
                couponService.getAllCoupons();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponResponseDto> getCouponById(
            @PathVariable Long id) {

        CouponResponseDto response =
                couponService.getCouponById(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponResponseDto> updateCoupon(
            @PathVariable Long id,
            @Valid @RequestBody CouponRequestDto request) {

        CouponResponseDto response =
                couponService.updateCoupon(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(
            @PathVariable Long id) {

        couponService.deleteCoupon(id);

        return ResponseEntity.noContent().build();
    }
}