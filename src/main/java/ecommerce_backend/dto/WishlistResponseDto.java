package ecommerce_backend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistResponseDto {

    private Long id;
    private Long userId;
    private List<WishlistItemResponseDto> items;
}