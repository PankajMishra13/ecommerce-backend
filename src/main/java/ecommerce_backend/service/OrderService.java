package ecommerce_backend.service;

import ecommerce_backend.dto.OrderRequestDto;
import ecommerce_backend.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {

    OrderResponseDto placeOrder(OrderRequestDto request);

    OrderResponseDto getOrderById(Long orderId);

    List<OrderResponseDto> getMyOrders();

    void cancelOrder(Long orderId);
}