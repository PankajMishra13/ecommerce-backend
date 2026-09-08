package ecommerce_backend.service.impl;

import ecommerce_backend.dto.OrderItemResponseDto;
import ecommerce_backend.dto.OrderRequestDto;
import ecommerce_backend.dto.OrderResponseDto;
import ecommerce_backend.entity.*;
import ecommerce_backend.exception.*;
import ecommerce_backend.mapper.OrderItemMapper;
import ecommerce_backend.mapper.OrderMapper;
import ecommerce_backend.repository.*;
import ecommerce_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductImageRepository productImageRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final InventoryRepository inventoryRepository;

    private User getCurrentUser() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UnauthorizedAccessException("Unauthorized access"));
    }

    @Transactional
    @Override
    public OrderResponseDto placeOrder(OrderRequestDto request) {

        User user = getCurrentUser();

        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() ->
                        new AddressNotFoundException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("Unauthorized access");
        }

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new CartNotFoundException("Cart not found"));

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        if (cartItems.isEmpty()) {
            throw new EmptyCartException("Cart is empty");
        }

        for (CartItem cartItem : cartItems) {

            Product product = cartItem.getProduct();

            Inventory inventory = inventoryRepository
                    .findByProductId(product.getId())
                    .orElseThrow(() ->
                            new InventoryNotFoundException("Inventory not found"));

            int availableQuantity =
                    inventory.getQuantity() - inventory.getReservedQuantity();

            if (availableQuantity < cartItem.getQuantity()) {
                throw new InsufficientStockException(
                        "Insufficient stock for product: " + product.getName()
                );
            }
        }

            for (CartItem cartItem : cartItems) {

                Product reservedProduct = cartItem.getProduct();

                Inventory inventory = inventoryRepository
                        .findByProductId(reservedProduct.getId())
                        .orElseThrow(() ->
                                new InventoryNotFoundException("Inventory not found"));

                inventory.setReservedQuantity(
                        inventory.getReservedQuantity()
                                + cartItem.getQuantity()
                );

                inventoryRepository.save(inventory);
            }

            BigDecimal subtotal = cartItems.stream()
                    .map(item -> item.getProduct().getSellingPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal discountAmount = BigDecimal.ZERO;
            BigDecimal shippingCharge = BigDecimal.ZERO;

            BigDecimal totalAmount = subtotal
                    .subtract(discountAmount)
                    .add(shippingCharge);

            String orderNumber = "ORD-" + UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 12)
                    .toUpperCase();

            Order order = Order.builder()
                    .user(user)
                    .orderNumber(orderNumber)
                    .orderStatus("PENDING")
                    .paymentStatus("PENDING")
                    .subtotal(subtotal)
                    .discountAmount(discountAmount)
                    .shippingCharge(shippingCharge)
                    .totalAmount(totalAmount)
                    .shippingFullName(address.getFullName())
                    .shippingMobile(address.getMobileNumber())
                    .shippingAddressLine1(address.getAddressLine1())
                    .shippingCity(address.getCity())
                    .shippingState(address.getState())
                    .shippingPostalCode(address.getPostalCode())
                    .shippingCountry(address.getCountry())
                    .build();

            order = orderRepository.save(order);

            for (CartItem cartItem : cartItems) {

                Product product = cartItem.getProduct();

                BigDecimal itemTotal = product.getSellingPrice()
                        .multiply(
                                BigDecimal.valueOf(cartItem.getQuantity())
                        );

                OrderItem orderItem = orderItemMapper.toEntity(
                        product,
                        cartItem.getQuantity(),
                        itemTotal,
                        order
                );

            productImageRepository.findByProductIdAndIsThumbnailTrue(product.getId())
                    .ifPresent(productImage ->
                            orderItem.setProductImageUrl(productImage.getImageUrl()));

            orderItemRepository.save(orderItem);
        }

        cartItemRepository.deleteAll(cartItems);

        List<OrderItem> orderItems =
                orderItemRepository.findByOrderId(order.getId());

        List<OrderItemResponseDto> itemDtos = orderItems.stream()
                .map(orderItemMapper::toResponseDto)
                .toList();

        return orderMapper.toResponseDto(order, itemDtos);
    }

    @Override
    public OrderResponseDto getOrderById(Long orderId) {

        User user = getCurrentUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("Unauthorized access");
        }

        List<OrderItem> orderItems =
                orderItemRepository.findByOrderId(order.getId());

        List<OrderItemResponseDto> itemDtos = orderItems.stream()
                .map(orderItemMapper::toResponseDto)
                .toList();

        return orderMapper.toResponseDto(order, itemDtos);
    }

    @Override
    public List<OrderResponseDto> getMyOrders() {

        User user = getCurrentUser();

        List<Order> orders = orderRepository.findByUserId(user.getId());

        return orders.stream()
                .map(order -> {

                    List<OrderItem> orderItems =
                            orderItemRepository.findByOrderId(order.getId());

                    List<OrderItemResponseDto> itemDtos =
                            orderItems.stream()
                                    .map(orderItemMapper::toResponseDto)
                                    .toList();

                    return orderMapper.toResponseDto(order, itemDtos);
                })
                .toList();
    }

    @Transactional
    @Override
    public void cancelOrder(Long orderId) {

        User user = getCurrentUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("Unauthorized access");
        }

        if (!order.getOrderStatus().equals("PENDING")) {
            throw new OrderCancellationException(
                    "Order cannot be cancelled"
            );
        }

        List<OrderItem> orderItems =
                orderItemRepository.findByOrderId(order.getId());

        for (OrderItem orderItem : orderItems) {

            Inventory inventory = inventoryRepository
                    .findByProductId(orderItem.getProduct().getId())
                    .orElseThrow(() ->
                            new InventoryNotFoundException("Inventory not found"));

            inventory.setReservedQuantity(
                    inventory.getReservedQuantity()
                            - orderItem.getQuantity()
            );

            inventoryRepository.save(inventory);
        }

        order.setOrderStatus("CANCELLED");

        orderRepository.save(order);
    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException("Order not found"));

        if (order.getOrderStatus().equals("CONFIRMED")
                && status.equals("SHIPPED")) {

            order.setOrderStatus("SHIPPED");

        } else if (order.getOrderStatus().equals("SHIPPED")
                && status.equals("DELIVERED")) {

            order.setOrderStatus("DELIVERED");

        } else {

            throw new OrderStatusException("Invalid order status transition");
        }

        orderRepository.save(order);

    }
}