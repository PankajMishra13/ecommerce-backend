package ecommerce_backend.controller;

import ecommerce_backend.dto.PaymentRequestDto;
import ecommerce_backend.dto.PaymentResponseDto;
import ecommerce_backend.service.PaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDto> initiatePayment(
            @Valid @RequestBody PaymentRequestDto request) {

        PaymentResponseDto response =
                paymentService.initiatePayment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{paymentId}/complete")
    public ResponseEntity<PaymentResponseDto> completePayment(
            @PathVariable Long paymentId) {

        PaymentResponseDto response =
                paymentService.completePayment(paymentId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{paymentId}/fail")
    public ResponseEntity<PaymentResponseDto> failPayment(
            @PathVariable Long paymentId,
            @RequestParam @NotBlank String failureReason) {

        PaymentResponseDto response =
                paymentService.failPayment(paymentId, failureReason);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsByOrderId(
            @PathVariable Long orderId) {

        List<PaymentResponseDto> response =
                paymentService.getPaymentsByOrderId(orderId);

        return ResponseEntity.ok(response);
    }
}