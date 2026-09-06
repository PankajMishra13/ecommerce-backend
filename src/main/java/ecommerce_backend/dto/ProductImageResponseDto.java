package ecommerce_backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageResponseDto {

    private Long id;
    private Long productId;
    private String imageUrl;
    private Integer displayOrder;
    private Boolean isThumbnail;
    private LocalDateTime createdAt;
}