package ecommerce_backend.mapper;

import ecommerce_backend.dto.WishlistItemResponseDto;
import ecommerce_backend.entity.WishlistItem;
import org.springframework.stereotype.Component;

@Component
public class WishlistItemMapper {

    public WishlistItemResponseDto toResponseDto(WishlistItem wishlistItem) {

        return WishlistItemResponseDto.builder()
                .id(wishlistItem.getId())
                .productId(wishlistItem.getProduct().getId())
                .build();
    }
}