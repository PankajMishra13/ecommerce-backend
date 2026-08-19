package ecommerce_backend.mapper;

import ecommerce_backend.dto.UserRequestDto;
import ecommerce_backend.dto.UserResponseDto;
import ecommerce_backend.entity.User;

public class UserMapper {

    public static User toEntity(UserRequestDto dto){

        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .gender(dto.getGender())
                .email(dto.getEmail())
                .mobile(dto.getMobile())
                .password(dto.getPassword())
                .build();
    }

    public static UserResponseDto toResponseDto(User user) {

        return UserResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(user.getGender())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .role(user.getRole().getName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
