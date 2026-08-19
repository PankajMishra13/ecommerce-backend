package ecommerce_backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandResponseDto {

    private Long id;
    private String name;
    private String description;
    private String websiteUrl;
    private String country;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
