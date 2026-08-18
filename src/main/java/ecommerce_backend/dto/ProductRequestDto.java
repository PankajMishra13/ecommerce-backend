package ecommerce_backend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    @NotNull
    private Long categoryId;

    @NotNull
    private Long brandId;

    @NotBlank
    @Size(max = 255)
    private String name;

    @Size(max = 500)
    private String shortDescription;

    private String description;

    @NotBlank
    @Size(max = 100)
    private String sku;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal mrp;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal sellingPrice;

    private String status;

}