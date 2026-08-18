package ecommerce_backend.service;

import ecommerce_backend.dto.AddressRequestDto;
import ecommerce_backend.dto.AddressResponseDto;

import java.util.List;

public interface AddressService {

    AddressResponseDto createAddress(AddressRequestDto request);

    List<AddressResponseDto> getMyAddresses();

    AddressResponseDto getAddressById(Long id);

    AddressResponseDto updateAddress(Long id, AddressRequestDto request);

    void deleteAddress(Long id);
}
