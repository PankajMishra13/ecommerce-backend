package ecommerce_backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDto {

    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
    @Max(5)
    private Byte rating;

    private String reviewTitle;

    private String reviewComment;
}