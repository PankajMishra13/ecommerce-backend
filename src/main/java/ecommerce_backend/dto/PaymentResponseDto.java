package ecommerce_backend.dto;

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
    private String paymentMethod;
    private String paymentStatus;
    private String transactionId;
    private String gatewayTransactionId;
    private BigDecimal amount;
    private LocalDateTime paymentInitiatedAt;
    private LocalDateTime paymentCompletedAt;
    private String failureReason;
    private LocalDateTime createdAt;
}