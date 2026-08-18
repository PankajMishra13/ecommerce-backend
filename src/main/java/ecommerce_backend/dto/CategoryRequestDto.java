package ecommerce_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto {

    private Long parentCategoryId;

    @NotBlank
    @Size(max=100)
    private String name;

    @Size(max=500)
    private String description;

    private Boolean isActive;

}
