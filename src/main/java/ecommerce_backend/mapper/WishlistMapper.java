package ecommerce_backend.mapper;

import ecommerce_backend.dto.WishlistResponseDto;
import ecommerce_backend.entity.Wishlist;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WishlistMapper {

    public WishlistResponseDto toResponseDto(
            Wishlist wishlist,
            List<ecommerce_backend.dto.WishlistItemResponseDto> items) {

        return WishlistResponseDto.builder()
                .id(wishlist.getId())
                .userId(wishlist.getUser().getId())
                .items(items)
                .build();
    }
}