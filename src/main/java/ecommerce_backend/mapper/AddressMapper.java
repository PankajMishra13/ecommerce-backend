package ecommerce_backend.mapper;

import ecommerce_backend.dto.AddressRequestDto;
import ecommerce_backend.dto.AddressResponseDto;
import ecommerce_backend.entity.Address;

public class AddressMapper {

    public static Address toEntity(AddressRequestDto request) {

        Address address = new Address();

        address.setAddressType(request.getAddressType());
        address.setFullName(request.getFullName());
        address.setMobileNumber(request.getMobileNumber());
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setLandmark(request.getLandmark());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());
        address.setIsDefault(request.getIsDefault());

        return address;
    }

    public static AddressResponseDto toResponseDto(Address address) {

        AddressResponseDto response = new AddressResponseDto();

        response.setId(address.getId());
        response.setAddressType(address.getAddressType());
        response.setFullName(address.getFullName());
        response.setMobileNumber(address.getMobileNumber());
        response.setAddressLine1(address.getAddressLine1());
        response.setAddressLine2(address.getAddressLine2());
        response.setLandmark(address.getLandmark());
        response.setCity(address.getCity());
        response.setState(address.getState());
        response.setPostalCode(address.getPostalCode());
        response.setCountry(address.getCountry());
        response.setIsDefault(address.getIsDefault());
        response.setCreatedAt(address.getCreatedAt());
        response.setUpdatedAt(address.getUpdatedAt());

        return response;
    }
}
