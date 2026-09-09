package ecommerce_backend.controller;

import ecommerce_backend.dto.InventoryRequestDto;
import ecommerce_backend.dto.InventoryResponseDto;
import ecommerce_backend.dto.InventoryUpdateRequestDto;
import ecommerce_backend.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryResponseDto> createInventory(
            @Valid @RequestBody InventoryRequestDto requestDto) {

        InventoryResponseDto response =
                inventoryService.createInventory(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
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
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {

        inventoryService.deleteInventory(id);

        return ResponseEntity.noContent().build();
    }

}
