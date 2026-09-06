package ecommerce_backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSpecificationRequestDto {

    @NotNull
    private Long productId;

    @NotBlank
    private String specificationName;

    @NotBlank
    private String specificationValue;

    @Min(1)
    private Integer displayOrder;
}
