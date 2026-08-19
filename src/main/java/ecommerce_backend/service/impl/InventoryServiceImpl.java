package ecommerce_backend.service.impl;

import ecommerce_backend.dto.InventoryRequestDto;
import ecommerce_backend.dto.InventoryResponseDto;
import ecommerce_backend.dto.InventoryUpdateRequestDto;
import ecommerce_backend.entity.Inventory;
import ecommerce_backend.entity.Product;
import ecommerce_backend.exception.InventoryAlreadyExistsException;
import ecommerce_backend.exception.InventoryNotFoundException;
import ecommerce_backend.exception.ProductNotFoundException;
import ecommerce_backend.mapper.InventoryMapper;
import ecommerce_backend.repository.InventoryRepository;
import ecommerce_backend.repository.ProductRepository;
import ecommerce_backend.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    public InventoryResponseDto createInventory(InventoryRequestDto request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (inventoryRepository.findByProductId(request.getProductId()).isPresent()) {
            throw new InventoryAlreadyExistsException("Inventory already exists for this product");
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
                        new InventoryNotFoundException("Inventory not found"));

        return inventoryMapper.toResponseDto(inventory);
    }

    @Override
    public InventoryResponseDto updateInventory(
            Long id,
            InventoryUpdateRequestDto request) {

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new InventoryNotFoundException("Inventory not found"));

        inventory.setQuantity(request.getQuantity());

        inventory.setReorderLevel(request.getReorderLevel());

        Inventory updatedInventory =
                inventoryRepository.save(inventory);

        return inventoryMapper.toResponseDto(updatedInventory);
    }


    @Override
    public void deleteInventory(Long id) {

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new InventoryNotFoundException("Inventory not found"));

        inventoryRepository.delete(inventory);

    }
}
