package ecommerce_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String mobile;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    }
