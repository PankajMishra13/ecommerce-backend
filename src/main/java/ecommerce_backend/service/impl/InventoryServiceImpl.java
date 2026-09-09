package ecommerce_backend.service.impl;

import ecommerce_backend.dto.InventoryRequestDto;
import ecommerce_backend.dto.InventoryResponseDto;
import ecommerce_backend.dto.InventoryUpdateRequestDto;
import ecommerce_backend.entity.Inventory;
import ecommerce_backend.entity.Product;
import ecommerce_backend.exception.ConflictException;
import ecommerce_backend.exception.ResourceNotFoundException;
import ecommerce_backend.mapper.InventoryMapper;
import ecommerce_backend.repository.InventoryRepository;
import ecommerce_backend.repository.ProductRepository;
import ecommerce_backend.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    @Transactional
    public InventoryResponseDto createInventory(InventoryRequestDto request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (inventoryRepository.findByProductId(request.getProductId()).isPresent()) {
            throw new ConflictException("Inventory already exists for this product");
        }

        Inventory inventory = inventoryMapper.toEntity(request);

        inventory.setProduct(product);

        Inventory savedInventory = inventoryRepository.save(inventory);

        return inventoryMapper.toResponseDto(savedInventory);
    }

    @Override
    public List<InventoryResponseDto> getAllInventories() {

        List<Inventory> inventories = inventoryRepository.findAll();

        return inventories.stream()
                .map(inventoryMapper::toResponseDto)
                .toList();
    }

    @Override
    public InventoryResponseDto getInventoryById(Long id) {

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory not found"));

        return inventoryMapper.toResponseDto(inventory);
    }

    @Override
    @Transactional
    public InventoryResponseDto updateInventory(
            Long id,
            InventoryUpdateRequestDto request) {

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory not found"));

        inventory.setQuantity(request.getQuantity());

        inventory.setReorderLevel(request.getReorderLevel());

        Inventory updatedInventory =
                inventoryRepository.save(inventory);

        return inventoryMapper.toResponseDto(updatedInventory);
    }


    @Override
    @Transactional
    public void deleteInventory(Long id) {

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory not found"));

        inventoryRepository.delete(inventory);

    }
}
