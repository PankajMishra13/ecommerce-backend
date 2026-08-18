package ecommerce_backend.service.impl;

import ecommerce_backend.dto.CategoryRequestDto;
import ecommerce_backend.dto.CategoryResponseDto;
import ecommerce_backend.entity.Category;
import ecommerce_backend.exception.CategoryNotFoundException;
import ecommerce_backend.exception.InvalidCategoryException;
import ecommerce_backend.mapper.CategoryMapper;
import ecommerce_backend.repository.CategoryRepository;
import ecommerce_backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto requestDto) {

        Category category = categoryMapper.toEntity(requestDto);

        if (requestDto.getParentCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(
                    requestDto.getParentCategoryId()
            ).orElseThrow(() ->
                    new CategoryNotFoundException(
                            "Parent category not found with id: "
                                    + requestDto.getParentCategoryId()
                    )
            );

            category.setParentCategory(parentCategory);
        }

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponseDto(savedCategory);

    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(categoryMapper::toResponseDto)
                .toList();
    }

    @Override
    public CategoryResponseDto getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category not found with id: " + id)
                );

        return categoryMapper.toResponseDto(category);

    }

    @Override
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto requestDto) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category not found with id: " + id)
                );

        category.setName(requestDto.getName());
        category.setDescription(requestDto.getDescription());
        category.setIsActive(requestDto.getIsActive());

        if (requestDto.getParentCategoryId() != null) {

            if (id.equals(requestDto.getParentCategoryId())) {
                throw new InvalidCategoryException("A category cannot be its own parent"
                );
            }

            Category parentCategory = categoryRepository.findById(
                    requestDto.getParentCategoryId()
            ).orElseThrow(() ->
                    new CategoryNotFoundException("Parent category not found with id: "
                            + requestDto.getParentCategoryId())
            );

            category.setParentCategory(parentCategory);

        } else {
            category.setParentCategory(null);
        }

        Category updatedCategory = categoryRepository.save(category);

        return categoryMapper.toResponseDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category not found with id: " + id)
                );

        if (categoryRepository.existsByParentCategoryId(id)) {
            throw new InvalidCategoryException(
                    "Cannot delete category because it has child categories"
            );
        }

        categoryRepository.delete(category);
    }
}
