package ecommerce_backend.service;

import ecommerce_backend.dto.PaymentRequestDto;
import ecommerce_backend.dto.PaymentResponseDto;

import java.util.List;

public interface PaymentService {

    PaymentResponseDto initiatePayment(PaymentRequestDto request);

    PaymentResponseDto completePayment(Long paymentId);

    PaymentResponseDto failPayment(Long paymentId, String failureReason);

    List<PaymentResponseDto> getPaymentsByOrderId(Long orderId);
}