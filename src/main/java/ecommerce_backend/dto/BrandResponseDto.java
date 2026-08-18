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
