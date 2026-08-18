package ecommerce_backend.mapper;

import ecommerce_backend.dto.UserRequestDto;
import ecommerce_backend.dto.UserResponseDto;
import ecommerce_backend.entity.User;

public class UserMapper {

    public static User toEntity(UserRequestDto dto){

        User user = new User();

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setGender(dto.getGender());
        user.setEmail(dto.getEmail());
        user.setMobile(dto.getMobile());
        user.setPassword(dto.getPassword());

        return user;
    }

    public static UserResponseDto toResponseDto(User user) {

        UserResponseDto response = new UserResponseDto();

        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setGender(user.getGender());
        response.setEmail(user.getEmail());
        response.setMobile(user.getMobile());
        response.setRole(user.getRole().getName());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());

        return response;
    }
}
