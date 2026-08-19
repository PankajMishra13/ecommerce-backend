package ecommerce_backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {

    private Long id;

    private Long categoryId;

    private Long brandId;

    private String name;

    private String shortDescription;

    private String description;

    private String sku;

    private BigDecimal mrp;

    private BigDecimal sellingPrice;

    private String status;

    private Boolean isDeleted;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}