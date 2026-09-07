package ecommerce_backend.mapper;

import ecommerce_backend.dto.OrderItemResponseDto;
import ecommerce_backend.entity.OrderItem;
import ecommerce_backend.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    public OrderItemResponseDto toResponseDto(OrderItem orderItem) {

        Long productId = null;

        if (orderItem.getProduct() != null) {
            productId = orderItem.getProduct().getId();
        }

        return OrderItemResponseDto.builder()
                .id(orderItem.getId())
                .productId(productId)
                .productName(orderItem.getProductName())
                .productSku(orderItem.getProductSku())
                .productPrice(orderItem.getProductPrice())
                .quantity(orderItem.getQuantity())
                .totalPrice(orderItem.getTotalPrice())
                .productImageUrl(orderItem.getProductImageUrl())
                .build();
    }

    public OrderItem toEntity(
            Product product,
            Integer quantity,
            java.math.BigDecimal totalPrice,
            ecommerce_backend.entity.Order order) {

        return OrderItem.builder()
                .order(order)
                .product(product)
                .productName(product.getName())
                .productSku(product.getSku())
                .productPrice(product.getSellingPrice())
                .quantity(quantity)
                .totalPrice(totalPrice)
                .build();
    }
}