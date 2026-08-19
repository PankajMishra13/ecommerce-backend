package ecommerce_backend.controller;

import ecommerce_backend.dto.InventoryRequestDto;
import ecommerce_backend.dto.InventoryResponseDto;
import ecommerce_backend.dto.InventoryUpdateRequestDto;
import ecommerce_backend.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public InventoryResponseDto createInventory(
            @Valid @RequestBody InventoryRequestDto requestDto) {

        return inventoryService.createInventory(requestDto);
    }

    @GetMapping
    public List<InventoryResponseDto> getAllInventories() {

        return inventoryService.getAllInventories();
    }

    @GetMapping("/{id}")
    public InventoryResponseDto getInventoryById(@PathVariable Long id) {

        return inventoryService.getInventoryById(id);
    }

    @PutMapping("/{id}")
    public InventoryResponseDto updateInventory(
            @PathVariable Long id,
            @Valid @RequestBody InventoryUpdateRequestDto requestDto) {

        return inventoryService.updateInventory(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteInventory(@PathVariable Long id) {

        inventoryService.deleteInventory(id);
    }

}
