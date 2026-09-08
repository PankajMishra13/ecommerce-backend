package ecommerce_backend.service.impl;

import ecommerce_backend.dto.PaymentRequestDto;
import ecommerce_backend.dto.PaymentResponseDto;
import ecommerce_backend.entity.*;
import ecommerce_backend.exception.InventoryNotFoundException;
import ecommerce_backend.exception.OrderNotFoundException;
import ecommerce_backend.exception.PaymentException;
import ecommerce_backend.exception.UnauthorizedAccessException;
import ecommerce_backend.mapper.PaymentMapper;
import ecommerce_backend.repository.*;
import ecommerce_backend.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PaymentMapper paymentMapper;
    private final InventoryRepository inventoryRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public PaymentResponseDto initiatePayment(PaymentRequestDto request) {

        User user = getCurrentUser();

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() ->
                        new OrderNotFoundException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("Unauthorized access");
        }

        if (!order.getOrderStatus().equals("PENDING")) {
            throw new PaymentException("Order is not eligible for payment");
        }

        List<Payment> existingPayments =
                paymentRepository.findByOrderId(order.getId());

        boolean paymentInProgress = existingPayments.stream()
                .anyMatch(payment ->
                        payment.getPaymentStatus().equals("INITIATED"));

        if (paymentInProgress) {
            throw new PaymentException("Payment is already in progress");
        }

        boolean paymentSuccessful = existingPayments.stream()
                .anyMatch(payment ->
                        payment.getPaymentStatus().equals("SUCCESS"));

        if (paymentSuccessful) {
            throw new PaymentException("Order is already paid");
        }

        boolean paymentFailed = existingPayments.stream()
                .anyMatch(payment ->
                        payment.getPaymentStatus().equals("FAILED"));

        if (paymentFailed) {

            for (OrderItem orderItem : orderItemRepository.findByOrderId(order.getId())) {

                Inventory inventory = inventoryRepository
                        .findByProductId(orderItem.getProduct().getId())
                        .orElseThrow(() ->
                                new InventoryNotFoundException("Inventory not found"));

                int availableQuantity =
                        inventory.getQuantity() - inventory.getReservedQuantity();

                if (availableQuantity < orderItem.getQuantity()) {
                    throw new PaymentException("Insufficient stock for payment retry");
                }

                inventory.setReservedQuantity(
                        inventory.getReservedQuantity() + orderItem.getQuantity()
                );

                inventoryRepository.save(inventory);
            }
        }

        Payment payment = Payment.builder()
                .order(order)
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus("INITIATED")
                .amount(order.getTotalAmount())
                .build();

        payment = paymentRepository.save(payment);

        return paymentMapper.toResponseDto(payment);
    }

    private User getCurrentUser() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UnauthorizedAccessException("Unauthorized access"));
    }

    @Override
    @Transactional
    public PaymentResponseDto completePayment(Long paymentId) {

        Payment payment = paymentRepository.findPaymentByIdWithLock(paymentId)
                .orElseThrow(() ->
                        new PaymentException("Payment not found"));

        if (!payment.getPaymentStatus().equals("INITIATED")) {
            throw new PaymentException("Payment is not in progress");
        }

        Order order = payment.getOrder();

        for (OrderItem orderItem : orderItemRepository.findByOrderId(order.getId())) {

            Inventory inventory = inventoryRepository
                    .findByProductId(orderItem.getProduct().getId())
                    .orElseThrow(() ->
                            new InventoryNotFoundException("Inventory not found"));

            if (inventory.getReservedQuantity() < orderItem.getQuantity()) {
                throw new PaymentException("Insufficient reserved stock for payment completion");
            }

            inventory.setQuantity(
                    inventory.getQuantity() - orderItem.getQuantity()
            );

            inventory.setReservedQuantity(
                    inventory.getReservedQuantity() - orderItem.getQuantity()
            );

            inventoryRepository.save(inventory);
        }

        payment.setPaymentStatus("SUCCESS");
        payment.setPaymentCompletedAt(LocalDateTime.now());

        order.setPaymentStatus("SUCCESS");
        order.setOrderStatus("CONFIRMED");

        paymentRepository.save(payment);
        orderRepository.save(order);

        return paymentMapper.toResponseDto(payment);
    }

    @Override
    @Transactional
    public PaymentResponseDto failPayment(Long paymentId, String failureReason) {

        Payment payment = paymentRepository.findPaymentByIdWithLock(paymentId)
                .orElseThrow(() ->
                        new PaymentException("Payment not found"));

        if (!payment.getPaymentStatus().equals("INITIATED")) {
            throw new PaymentException("Payment is not in progress");
        }

        Order order = payment.getOrder();

        for (OrderItem orderItem : orderItemRepository.findByOrderId(order.getId())) {

            Inventory inventory = inventoryRepository
                    .findByProductId(orderItem.getProduct().getId())
                    .orElseThrow(() ->
                            new InventoryNotFoundException("Inventory not found"));

            if (inventory.getReservedQuantity() < orderItem.getQuantity()) {
                throw new PaymentException("Insufficient reserved stock for payment failure");
            }

            inventory.setReservedQuantity(
                    inventory.getReservedQuantity() - orderItem.getQuantity()
            );

            inventoryRepository.save(inventory);
        }

        payment.setPaymentStatus("FAILED");
        payment.setFailureReason(failureReason);

        order.setPaymentStatus("FAILED");

        paymentRepository.save(payment);
        orderRepository.save(order);

        return paymentMapper.toResponseDto(payment);
    }

    @Override
    public List<PaymentResponseDto> getPaymentsByOrderId(Long orderId) {

        User user = getCurrentUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("Unauthorized access");
        }

        List<Payment> payments =
                paymentRepository.findByOrderId(orderId);

        return payments.stream()
                .map(paymentMapper::toResponseDto)
                .toList();
    }
}