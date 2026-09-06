package ecommerce_backend.mapper;

import ecommerce_backend.dto.CartItemResponseDto;
import ecommerce_backend.dto.CartResponseDto;
import ecommerce_backend.entity.Cart;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartMapper {

    public CartResponseDto toResponseDto(
            Cart cart,
            List<CartItemResponseDto> items) {

        Long userId = null;

        if (cart.getUser() != null) {
            userId = cart.getUser().getId();
        }

        return CartResponseDto.builder()
                .id(cart.getId())
                .userId(userId)
                .status(cart.getStatus())
                .items(items)
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .build();
    }
}