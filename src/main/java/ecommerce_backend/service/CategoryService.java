package ecommerce_backend.service;

import ecommerce_backend.dto.CategoryRequestDto;
import ecommerce_backend.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

CategoryResponseDto createCategory(CategoryRequestDto requestDto);

List<CategoryResponseDto> getAllCategories();

CategoryResponseDto getCategoryById(Long id);

CategoryResponseDto updateCategory(Long id, CategoryRequestDto requestDto);

void deleteCategory(Long id);
}
