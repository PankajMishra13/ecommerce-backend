package ecommerce_backend.service.impl;

import ecommerce_backend.dto.CouponRequestDto;
import ecommerce_backend.dto.CouponResponseDto;
import ecommerce_backend.entity.Coupon;
import ecommerce_backend.enums.DiscountType;
import ecommerce_backend.exception.CouponException;
import ecommerce_backend.mapper.CouponMapper;
import ecommerce_backend.repository.CouponRepository;
import ecommerce_backend.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;

    @Transactional
    @Override
    public CouponResponseDto createCoupon(CouponRequestDto request) {

        validateCoupon(request);

        Optional<Coupon> existingCoupon =
                couponRepository.findByCode(request.getCode());

        if (existingCoupon.isPresent()) {
            throw new CouponException("Coupon code already exists");
        }

        Coupon coupon = Coupon.builder()
                .code(request.getCode())
                .description(request.getDescription())
                .discountType(request.getDiscountType())
                .discountValue(request.getDiscountValue())
                .minimumOrderAmount(request.getMinimumOrderAmount())
                .maximumDiscountAmount(request.getMaximumDiscountAmount())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .usageLimit(request.getUsageLimit())
                .usedCount(0)
                .isActive(true)
                .build();

        coupon = couponRepository.save(coupon);

        return couponMapper.toResponseDto(coupon);
    }

    @Override
    public List<CouponResponseDto> getAllCoupons() {

        List<Coupon> coupons = couponRepository.findAll();

        return coupons.stream()
                .map(couponMapper::toResponseDto)
                .toList();
    }

    @Override
    public CouponResponseDto getCouponById(Long couponId) {

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() ->
                        new CouponException("Coupon not found"));

        return couponMapper.toResponseDto(coupon);
    }

    @Transactional
    @Override
    public CouponResponseDto updateCoupon(
            Long couponId,
            CouponRequestDto request) {

        validateCoupon(request);

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() ->
                        new CouponException("Coupon not found"));

        Optional<Coupon> existingCoupon =
                couponRepository.findByCode(request.getCode());

        if (existingCoupon.isPresent()
                && !existingCoupon.get().getId().equals(couponId)) {

            throw new CouponException("Coupon code already exists");
        }

        coupon.setCode(request.getCode());
        coupon.setDescription(request.getDescription());
        coupon.setDiscountType(request.getDiscountType());
        coupon.setDiscountValue(request.getDiscountValue());
        coupon.setMinimumOrderAmount(request.getMinimumOrderAmount());
        coupon.setMaximumDiscountAmount(request.getMaximumDiscountAmount());
        coupon.setStartDate(request.getStartDate());
        coupon.setEndDate(request.getEndDate());
        coupon.setUsageLimit(request.getUsageLimit());

        coupon = couponRepository.save(coupon);

        return couponMapper.toResponseDto(coupon);
    }

    @Transactional
    @Override
    public void deleteCoupon(Long couponId) {

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() ->
                        new CouponException("Coupon not found"));

        couponRepository.delete(coupon);
    }

    private void validateCoupon(CouponRequestDto request) {

        if (request.getDiscountType() == DiscountType.PERCENTAGE
                && request.getDiscountValue().compareTo(new BigDecimal("100")) > 0) {

            throw new CouponException(
                    "Percentage discount cannot exceed 100");
        }

        if (request.getStartDate().isAfter(request.getEndDate())) {

            throw new CouponException(
                    "Start date must be before end date");
        }

        if (request.getUsageLimit() != null
                && request.getUsageLimit() < 0) {

            throw new CouponException(
                    "Usage limit cannot be negative");
        }
    }
}