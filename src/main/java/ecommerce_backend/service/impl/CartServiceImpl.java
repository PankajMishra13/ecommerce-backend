package ecommerce_backend.service.impl;

import ecommerce_backend.dto.CartItemRequestDto;
import ecommerce_backend.dto.CartItemResponseDto;
import ecommerce_backend.dto.CartResponseDto;
import ecommerce_backend.entity.Cart;
import ecommerce_backend.entity.CartItem;
import ecommerce_backend.entity.Product;
import ecommerce_backend.entity.User;
import ecommerce_backend.exception.CartItemNotFoundException;
import ecommerce_backend.exception.CartNotFoundException;
import ecommerce_backend.exception.ProductNotFoundException;
import ecommerce_backend.exception.UnauthorizedAccessException;
import ecommerce_backend.repository.CartItemRepository;
import ecommerce_backend.repository.CartRepository;
import ecommerce_backend.repository.ProductRepository;
import ecommerce_backend.repository.UserRepository;
import ecommerce_backend.mapper.CartItemMapper;
import ecommerce_backend.mapper.CartMapper;
import ecommerce_backend.service.CartService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public CartResponseDto getMyCart() {

        User user = getCurrentUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        List<CartItemResponseDto> itemDtos = cartItems.stream()
                .map(cartItemMapper::toResponseDto)
                .toList();

        return cartMapper.toResponseDto(cart, itemDtos);
    }

    @Transactional
    @Override
    public CartResponseDto addItemToCart(CartItemRequestDto request) {

        User user = getCurrentUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .user(user)
                            .status("ACTIVE")
                            .build();

                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), product.getId())
                .orElse(null);

        if (cartItem != null) {

            cartItem.setQuantity(
                    cartItem.getQuantity() + request.getQuantity()
            );

        } else {

            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();
        }

        cartItemRepository.save(cartItem);

        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);

        return getMyCart();
    }

    @Transactional
    @Override
    public CartResponseDto updateCartItem(Long cartItemId, Integer quantity) {

        User user = getCurrentUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new CartItemNotFoundException("Cart item not found"));

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new CartItemNotFoundException("Cart item not found");
        }

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);


        return getMyCart();
    }

    @Transactional
    @Override
    public void removeCartItem(Long cartItemId) {

        User user = getCurrentUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new CartItemNotFoundException("Cart item not found"));

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new CartItemNotFoundException("Cart item not found");
        }

        cartItemRepository.delete(cartItem);

        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart() {

        User user = getCurrentUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        cartItemRepository.deleteAll(cartItems);

        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
    }

    private User getCurrentUser() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UnauthorizedAccessException("Unauthorized access"));
    }
}