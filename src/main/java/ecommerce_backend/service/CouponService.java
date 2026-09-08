package ecommerce_backend.service;

import ecommerce_backend.dto.CouponRequestDto;
import ecommerce_backend.dto.CouponResponseDto;

import java.util.List;

public interface CouponService {

    CouponResponseDto createCoupon(CouponRequestDto request);

    List<CouponResponseDto> getAllCoupons();

    CouponResponseDto getCouponById(Long couponId);

    CouponResponseDto updateCoupon(Long couponId, CouponRequestDto request);

    void deleteCoupon(Long couponId);
}