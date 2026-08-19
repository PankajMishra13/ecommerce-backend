package ecommerce_backend.mapper;

import ecommerce_backend.dto.InventoryRequestDto;
import ecommerce_backend.dto.InventoryResponseDto;
import ecommerce_backend.entity.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {

    public Inventory toEntity(InventoryRequestDto dto) {

        return Inventory.builder()
                .quantity(dto.getQuantity())
                .reorderLevel(dto.getReorderLevel())
                .build();
    }

    public InventoryResponseDto toResponseDto(Inventory inventory) {

        Long productId = null;

        if (inventory.getProduct() != null) {
            productId = inventory.getProduct().getId();
        }

        return InventoryResponseDto.builder()
                .id(inventory.getId())
                .productId(productId)
                .quantity(inventory.getQuantity())
                .reservedQuantity(inventory.getReservedQuantity())
                .reorderLevel(inventory.getReorderLevel())
                .createdAt(inventory.getCreatedAt())
                .updatedAt(inventory.getUpdatedAt())
                .build();
    }
}