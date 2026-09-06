package ecommerce_backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSpecificationResponseDto {

    private Long id;
    private Long productId;
    private String specificationName;
    private String specificationValue;
    private Integer displayOrder;
    private LocalDateTime createdAt;
}