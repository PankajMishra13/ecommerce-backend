package ecommerce_backend.controller;

import ecommerce_backend.dto.BrandRequestDto;
import ecommerce_backend.dto.BrandResponseDto;
import ecommerce_backend.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<BrandResponseDto> createBrand(
            @Valid @RequestBody BrandRequestDto requestDto) {

        return ResponseEntity.ok(
                brandService.createBrand(requestDto)
        );
    }

    @GetMapping
    public ResponseEntity<List<BrandResponseDto>> getAllBrands() {

        return ResponseEntity.ok(
                brandService.getAllBrands()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandResponseDto> getBrandById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                brandService.getBrandById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandResponseDto> updateBrand(
            @PathVariable Long id,
            @Valid @RequestBody BrandRequestDto requestDto) {

        return ResponseEntity.ok(
                brandService.updateBrand(id, requestDto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(
            @PathVariable Long id) {

        brandService.deleteBrand(id);

        return ResponseEntity.noContent().build();
    }
}

