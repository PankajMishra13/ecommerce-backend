package ecommerce_backend.mapper;

import ecommerce_backend.dto.CategoryRequestDto;
import ecommerce_backend.dto.CategoryResponseDto;
import ecommerce_backend.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequestDto dto) {

        return Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .isActive(dto.getIsActive())
                .build();
    }

    public CategoryResponseDto toResponseDto(Category category){

        Long parentCategoryId = null;

        if (category.getParentCategory() != null) {
            parentCategoryId = category.getParentCategory().getId();
        }

        return CategoryResponseDto.builder()
                .id(category.getId())
                .parentCategoryId(parentCategoryId)
                .name(category.getName())
                .description(category.getDescription())
                .isActive(category.getIsActive())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();

    }
}
