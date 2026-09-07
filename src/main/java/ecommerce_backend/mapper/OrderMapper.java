package ecommerce_backend.mapper;

import ecommerce_backend.dto.OrderItemResponseDto;
import ecommerce_backend.dto.OrderResponseDto;
import ecommerce_backend.entity.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderResponseDto toResponseDto(
            Order order,
            List<OrderItemResponseDto> items) {

        Long userId = null;

        if (order.getUser() != null) {
            userId = order.getUser().getId();
        }

        return OrderResponseDto.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .userId(userId)
                .orderStatus(order.getOrderStatus())
                .paymentStatus(order.getPaymentStatus())
                .subtotal(order.getSubtotal())
                .discountAmount(order.getDiscountAmount())
                .shippingCharge(order.getShippingCharge())
                .totalAmount(order.getTotalAmount())
                .shippingFullName(order.getShippingFullName())
                .shippingMobile(order.getShippingMobile())
                .shippingAddressLine1(order.getShippingAddressLine1())
                .shippingCity(order.getShippingCity())
                .shippingState(order.getShippingState())
                .shippingPostalCode(order.getShippingPostalCode())
                .shippingCountry(order.getShippingCountry())
                .items(items)
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}