package ecommerce_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {

    private UserResponseDto user;
    private String token;
    private String refreshToken;

    }
