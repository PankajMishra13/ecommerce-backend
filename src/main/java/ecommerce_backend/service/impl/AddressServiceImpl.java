package ecommerce_backend.service.impl;

import ecommerce_backend.dto.AddressRequestDto;
import ecommerce_backend.dto.AddressResponseDto;
import ecommerce_backend.entity.Address;
import ecommerce_backend.entity.User;
import ecommerce_backend.exception.UnauthorizedAccessException;
import ecommerce_backend.mapper.AddressMapper;
import ecommerce_backend.repository.AddressRepository;
import ecommerce_backend.repository.UserRepository;
import ecommerce_backend.service.AddressService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;


    public AddressServiceImpl(AddressRepository addressRepository,
                              UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public AddressResponseDto createAddress(AddressRequestDto request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Address address = AddressMapper.toEntity(request);

        address.setUser(user);

        if (Boolean.TRUE.equals(request.getIsDefault())) {

            addressRepository.findByUserAndIsDefaultTrue(user)
                    .ifPresent(existingDefault -> {
                        existingDefault.setIsDefault(false);
                        addressRepository.save(existingDefault);
                    });
        }

        Address savedAddress = addressRepository.save(address);

        return AddressMapper.toResponseDto(savedAddress);
    }

    @Override
    public List<AddressResponseDto> getMyAddresses() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<Address> addresses =
                addressRepository.findByUser(user);

        return addresses.stream()
                .map(AddressMapper::toResponseDto)
                .toList();
    }

    @Override
    public AddressResponseDto getAddressById(Long id) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Address address = addressRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("You are not allowed to access this address");
        }

        return AddressMapper.toResponseDto(address);

    }

    @Transactional
    @Override
    public AddressResponseDto updateAddress(Long id, AddressRequestDto request) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Address address = addressRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Address not found"));


        if (!address.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException(
                    "You are not allowed to update this address"
            );
        }

        if (Boolean.TRUE.equals(request.getIsDefault())) {

            addressRepository.findByUserAndIsDefaultTrue(user)
                    .ifPresent(existingDefault -> {

                        if (!existingDefault.getId().equals(address.getId())) {
                            existingDefault.setIsDefault(false);
                            addressRepository.save(existingDefault);
                        }
                    });
        }

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

        Address updatedAddress =
                addressRepository.save(address);

        return AddressMapper.toResponseDto(updatedAddress);

    }

    @Transactional
    @Override
    public void deleteAddress(Long id) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Address address = addressRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException(
                    "You are not allowed to delete this address"
            );
        }

        addressRepository.delete(address);
    }
}
