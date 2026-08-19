package ecommerce_backend.dto;

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
    private String gender;
    private String email;
    private String mobile;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    }
