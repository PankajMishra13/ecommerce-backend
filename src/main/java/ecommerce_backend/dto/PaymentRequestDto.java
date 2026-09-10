package ecommerce_backend.dto;

import ecommerce_backend.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDto {

    @NotNull
    private Long orderId;

    @NotNull
    private PaymentMethod paymentMethod;
}