package ecommerce_backend.repository;

import ecommerce_backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderId(Long orderId);

    @Query("""
        SELECT oi
        FROM OrderItem oi
        WHERE oi.order.user.id = :userId
        AND oi.product.id = :productId
        AND oi.order.orderStatus = 'DELIVERED'
        """)
    Optional<OrderItem> findDeliveredOrderItem(
            @Param("userId") Long userId,
            @Param("productId") Long productId
    );

}