package ecommerce_backend.service.impl;

import ecommerce_backend.dto.WishlistItemResponseDto;
import ecommerce_backend.dto.WishlistResponseDto;
import ecommerce_backend.entity.Product;
import ecommerce_backend.entity.User;
import ecommerce_backend.entity.Wishlist;
import ecommerce_backend.entity.WishlistItem;
import ecommerce_backend.enums.ProductStatus;
import ecommerce_backend.exception.ConflictException;
import ecommerce_backend.exception.ResourceNotFoundException;
import ecommerce_backend.mapper.WishlistItemMapper;
import ecommerce_backend.mapper.WishlistMapper;
import ecommerce_backend.repository.ProductRepository;
import ecommerce_backend.repository.UserRepository;
import ecommerce_backend.repository.WishlistItemRepository;
import ecommerce_backend.repository.WishlistRepository;
import ecommerce_backend.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final UserRepository userRepository;
    private final WishlistMapper wishlistMapper;
    private final WishlistItemMapper wishlistItemMapper;
    private final ProductRepository productRepository;

    @Override
    public WishlistResponseDto getMyWishlist() {

        User user = getCurrentUser();

        Wishlist wishlist = wishlistRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Wishlist not found")
                );

        List<WishlistItemResponseDto> items =
                wishlistItemRepository.findByWishlistId(wishlist.getId())
                        .stream()
                        .map(wishlistItemMapper::toResponseDto)
                        .toList();

        return wishlistMapper.toResponseDto(wishlist, items);
    }

    private User getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found")
                );
    }

    @Override
    @Transactional
    public WishlistResponseDto addItem(Long productId) {

        User user = getCurrentUser();

        Wishlist wishlist = wishlistRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Wishlist newWishlist = Wishlist.builder()
                            .user(user)
                            .build();
                    return wishlistRepository.save(newWishlist);
                });

        Product product = productRepository
                .findByIdAndIsDeletedFalseAndStatus(productId, ProductStatus.ACTIVE)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found")
                );

        if (wishlistItemRepository
                .findByWishlistIdAndProductId(wishlist.getId(), productId)
                .isPresent()) {
            throw new ConflictException("Product already exists in wishlist");
        }

        WishlistItem wishlistItem = WishlistItem.builder()
                .wishlist(wishlist)
                .product(product)
                .build();

        wishlistItemRepository.save(wishlistItem);

        return getMyWishlist();
    }

    @Override
    @Transactional
    public void removeItem(Long productId) {

        User user = getCurrentUser();

        Wishlist wishlist = wishlistRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Wishlist not found")
                );

        WishlistItem wishlistItem = wishlistItemRepository
                .findByWishlistIdAndProductId(wishlist.getId(), productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found in wishlist")
                );

        wishlistItemRepository.delete(wishlistItem);
    }

    @Override
    @Transactional
    public void clearWishlist() {

        User user = getCurrentUser();

        Wishlist wishlist = wishlistRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Wishlist not found")
                );

        List<WishlistItem> wishlistItems =
                wishlistItemRepository.findByWishlistId(wishlist.getId());

        wishlistItemRepository.deleteAll(wishlistItems);
    }
}