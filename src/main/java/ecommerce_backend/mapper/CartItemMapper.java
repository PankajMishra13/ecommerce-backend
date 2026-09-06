package ecommerce_backend.mapper;

import ecommerce_backend.dto.CartItemResponseDto;
import ecommerce_backend.entity.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    public CartItemResponseDto toResponseDto(
            CartItem cartItem) {

        Long productId = null;

        if (cartItem.getProduct() != null) {
            productId = cartItem.getProduct().getId();
        }

        return CartItemResponseDto.builder()
                .id(cartItem.getId())
                .productId(productId)
                .quantity(cartItem.getQuantity())
                .build();
    }
}