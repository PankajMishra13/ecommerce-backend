package ecommerce_backend.mapper;

import ecommerce_backend.dto.PaymentResponseDto;
import ecommerce_backend.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentResponseDto toResponseDto(Payment payment) {

        Long orderId = null;

        if (payment.getOrder() != null) {
            orderId = payment.getOrder().getId();
        }

        return PaymentResponseDto.builder()
                .id(payment.getId())
                .orderId(orderId)
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .transactionId(payment.getTransactionId())
                .gatewayTransactionId(payment.getGatewayTransactionId())
                .amount(payment.getAmount())
                .paymentInitiatedAt(payment.getPaymentInitiatedAt())
                .paymentCompletedAt(payment.getPaymentCompletedAt())
                .failureReason(payment.getFailureReason())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}