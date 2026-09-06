package ecommerce_backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageRequestDto {

    @NotNull
    private Long productId;

    @NotBlank
    @Size(max = 500)
    private String imageUrl;

    @NotNull
    @Min(1)
    private Integer displayOrder;

    @NotNull
    private Boolean isThumbnail;
}