package ecommerce_backend.service;

import ecommerce_backend.dto.UserRequestDto;
import ecommerce_backend.dto.UserResponseDto;
import ecommerce_backend.entity.User;

import java.util.List;

public interface UserService {

    UserResponseDto createUser(UserRequestDto request);

    List<UserResponseDto> getAllUser();

    UserResponseDto getUserById(Long id);

}
