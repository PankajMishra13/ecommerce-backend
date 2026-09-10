package ecommerce_backend.service.impl;

import ecommerce_backend.dto.CategoryRequestDto;
import ecommerce_backend.dto.CategoryResponseDto;
import ecommerce_backend.entity.Category;
import ecommerce_backend.exception.InvalidCategoryException;
import ecommerce_backend.exception.ResourceNotFoundException;
import ecommerce_backend.mapper.CategoryMapper;
import ecommerce_backend.repository.CategoryRepository;
import ecommerce_backend.repository.ProductRepository;
import ecommerce_backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ProductRepository productRepository;


    @Override
    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto requestDto) {

        Category category = categoryMapper.toEntity(requestDto);

        if (requestDto.getParentCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(
                    requestDto.getParentCategoryId()
            ).orElseThrow(() ->
                    new ResourceNotFoundException(
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
                        new ResourceNotFoundException("Category not found with id: " + id)
                );

        return categoryMapper.toResponseDto(category);

    }

    @Override
    @Transactional
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto requestDto) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with id: " + id)
                );

        category.setName(requestDto.getName());
        category.setDescription(requestDto.getDescription());
        category.setIsActive(requestDto.getIsActive());

        if (requestDto.getParentCategoryId() != null) {

            Category parentCategory =
                    validateParentCategory(id, requestDto.getParentCategoryId());

            category.setParentCategory(parentCategory);

        } else {
            category.setParentCategory(null);
        }

        Category updatedCategory = categoryRepository.save(category);

        return categoryMapper.toResponseDto(updatedCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with id: " + id)
                );

        if (categoryRepository.existsByParentCategoryId(id)) {
            throw new InvalidCategoryException(
                    "Cannot delete category because it has child categories"
            );
        }

        if (productRepository.existsByCategoryId(id)) {
            throw new InvalidCategoryException(
                    "Cannot delete category because it has products"
            );
        }

        categoryRepository.delete(category);
    }

    private Category validateParentCategory(Long categoryId, Long parentCategoryId) {

        Category currentParent = categoryRepository.findById(parentCategoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Parent category not found with id: " + parentCategoryId
                        )
                );

        Category parentCategory = currentParent;

        while (currentParent != null) {

            if (categoryId.equals(currentParent.getId())) {
                throw new InvalidCategoryException(
                        "A category cannot have one of its child categories as parent"
                );
            }

            currentParent = currentParent.getParentCategory();
        }

        return parentCategory;
    }
}
