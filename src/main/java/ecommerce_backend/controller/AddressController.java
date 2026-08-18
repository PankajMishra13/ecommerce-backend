package ecommerce_backend.controller;


import ecommerce_backend.dto.AddressRequestDto;
import ecommerce_backend.dto.AddressResponseDto;
import ecommerce_backend.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public AddressResponseDto createAddress(@Valid @RequestBody AddressRequestDto request) {

        return addressService.createAddress(request);
    }

    @GetMapping
    public List<AddressResponseDto> getMyAddresses() {

        return addressService.getMyAddresses();
    }

    @GetMapping("/{id}")
    public AddressResponseDto getAddressById(@PathVariable Long id) {

        return addressService.getAddressById(id);
    }

    @PutMapping("/{id}")
    public AddressResponseDto updateAddress(
            @PathVariable Long id,
            @Valid @RequestBody AddressRequestDto request) {

        return addressService.updateAddress(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {

        addressService.deleteAddress(id);

        return ResponseEntity.noContent().build();
    }

}
