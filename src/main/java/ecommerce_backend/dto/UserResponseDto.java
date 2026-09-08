package ecommerce_backend.dto;

import ecommerce_backend.enums.Gender;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private String mobile;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    }
