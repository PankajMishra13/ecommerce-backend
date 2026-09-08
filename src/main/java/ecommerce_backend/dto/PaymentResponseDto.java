package ecommerce_backend.dto;

import ecommerce_backend.enums.PaymentMethod;
import ecommerce_backend.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDto {

    private Long id;
    private Long orderId;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private String transactionId;
    private String gatewayTransactionId;
    private BigDecimal amount;
    private LocalDateTime paymentInitiatedAt;
    private LocalDateTime paymentCompletedAt;
    private String failureReason;
    private LocalDateTime createdAt;
}