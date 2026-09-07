package ecommerce_backend.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDto {

    private Long id;
    private Long productId;
    private String productName;
    private String productSku;
    private BigDecimal productPrice;
    private Integer quantity;
    private BigDecimal totalPrice;
    private String productImageUrl;
}