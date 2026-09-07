package ecommerce_backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {

    private Long id;
    private String orderNumber;

    private Long userId;

    private String orderStatus;
    private String paymentStatus;

    private BigDecimal subtotal;
    private BigDecimal discountAmount;
    private BigDecimal shippingCharge;
    private BigDecimal totalAmount;

    private String shippingFullName;
    private String shippingMobile;
    private String shippingAddressLine1;
    private String shippingCity;
    private String shippingState;
    private String shippingPostalCode;
    private String shippingCountry;

    private List<OrderItemResponseDto> items;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}