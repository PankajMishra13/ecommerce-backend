package ecommerce_backend.mapper;

import ecommerce_backend.dto.CategoryRequestDto;
import ecommerce_backend.dto.CategoryResponseDto;
import ecommerce_backend.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequestDto dto) {

        Category category = new Category();

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setIsActive(dto.getIsActive());

        return category;

    }

    public CategoryResponseDto toResponseDto(Category category){

        CategoryResponseDto responseDto = new CategoryResponseDto();

        responseDto.setId(category.getId());

        if (category.getParentCategory() != null) {
            responseDto.setParentCategoryId(
                    category.getParentCategory().getId()
            );
        }

        responseDto.setName(category.getName());
        responseDto.setDescription(category.getDescription());
        responseDto.setIsActive(category.getIsActive());
        responseDto.setCreatedAt(category.getCreatedAt());
        responseDto.setUpdatedAt(category.getUpdatedAt());

        return responseDto;



    }
}
