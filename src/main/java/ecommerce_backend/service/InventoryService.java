package ecommerce_backend.service;

import ecommerce_backend.dto.InventoryRequestDto;
import ecommerce_backend.dto.InventoryResponseDto;
import ecommerce_backend.dto.InventoryUpdateRequestDto;

import java.util.List;

public interface InventoryService {

    InventoryResponseDto createInventory(InventoryRequestDto request);

    List<InventoryResponseDto> getAllInventories();

    InventoryResponseDto getInventoryById(Long id);

    InventoryResponseDto updateInventory(
            Long id,
            InventoryUpdateRequestDto request);

    void deleteInventory(Long id);
}
