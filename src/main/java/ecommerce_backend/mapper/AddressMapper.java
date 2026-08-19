package ecommerce_backend.mapper;

import ecommerce_backend.dto.AddressRequestDto;
import ecommerce_backend.dto.AddressResponseDto;
import ecommerce_backend.entity.Address;

public class AddressMapper {

    public static Address toEntity(AddressRequestDto request) {

        return Address.builder()
                .addressType(request.getAddressType())
                .fullName(request.getFullName())
                .mobileNumber(request.getMobileNumber())
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .landmark(request.getLandmark())
                .city(request.getCity())
                .state(request.getState())
                .postalCode(request.getPostalCode())
                .country(request.getCountry())
                .isDefault(request.getIsDefault())
                .build();
    }

    public static AddressResponseDto toResponseDto(Address address) {

        return AddressResponseDto.builder()
                .id(address.getId())
                .addressType(address.getAddressType())
                .fullName(address.getFullName())
                .mobileNumber(address.getMobileNumber())
                .addressLine1(address.getAddressLine1())
                .addressLine2(address.getAddressLine2())
                .landmark(address.getLandmark())
                .city(address.getCity())
                .state(address.getState())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .isDefault(address.getIsDefault())
                .createdAt(address.getCreatedAt())
                .updatedAt(address.getUpdatedAt())
                .build();
    }
}
