package com.alexcode.eatgo.application;

import com.alexcode.eatgo.domain.models.Category;
import com.alexcode.eatgo.domain.repository.CategoryRepository;
import com.alexcode.eatgo.exceptions.CategoryDuplicationException;
import com.alexcode.eatgo.exceptions.CategoryNotFoundException;
import com.alexcode.eatgo.interfaces.dto.CategoryRequestDto;
import com.alexcode.eatgo.interfaces.dto.CategoryResponseDto;
import com.alexcode.eatgo.network.SuccessCode;
import com.alexcode.eatgo.network.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.alexcode.eatgo.security.ApplicationUserRole.ADMIN;

@Service
@Transactional
public class CategoryService {

  private CategoryRepository categoryRepository;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public SuccessResponse<List<CategoryResponseDto>> list() {
    List<Category> categories = categoryRepository.findAll();
    return response(categories, HttpStatus.OK.value(), SuccessCode.OK);
  }

  public SuccessResponse<CategoryResponseDto> create(CategoryRequestDto request){
    String name = request.getName();

    if(categoryRepository.findByName(name).isPresent()) {
      throw new CategoryDuplicationException(name);
    }

    Category category = Category.builder()
            .name(name)
            .createdAt(LocalDateTime.now())
            .createdBy(ADMIN.name())
            .build();

    Category savedCategory = categoryRepository.save(category);

    return response(savedCategory, HttpStatus.CREATED.value(), SuccessCode.CATEGORY_CREATION_SUCCESS);
  }

  public SuccessResponse<CategoryResponseDto> update(CategoryRequestDto request, Long categoryId) {
    String name = request.getName();

    if(categoryRepository.findByName(name).isPresent()) {
      throw new CategoryDuplicationException(name);
    }

    Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new CategoryNotFoundException(categoryId));

    category.updateCategory(name);

    return response(category, HttpStatus.OK.value(), SuccessCode.CATEGORY_UPDATE_SUCCESS);
  }

  public SuccessResponse<?> delete(Long categoryId) {
    Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new CategoryNotFoundException(categoryId));

    categoryRepository.deleteById(category.getId());

    return SuccessResponse.OK(HttpStatus.OK.value(), SuccessCode.CATEGORY_DELETE_SUCCESS);
  }

  private SuccessResponse<List<CategoryResponseDto>> response(List<Category> categories, Integer status, SuccessCode successCode) {
    List<CategoryResponseDto> data = categories.stream()
            .map(category -> CategoryResponseDto.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .createdAt(category.getCreatedAt())
                    .createdBy(category.getCreatedBy())
                    .updatedAt(category.getUpdatedAt())
                    .updatedBy(category.getUpdatedBy())
                    .build())
            .collect(Collectors.toList());

    return SuccessResponse.OK(data, status, successCode);
  }

  private SuccessResponse<CategoryResponseDto> response(Category category, Integer status, SuccessCode successCode) {
    CategoryResponseDto data = CategoryResponseDto.builder()
            .id(category.getId())
            .name(category.getName())
            .createdAt(category.getCreatedAt())
            .createdBy(category.getCreatedBy())
            .updatedAt(category.getUpdatedAt())
            .updatedBy(category.getUpdatedBy())
            .build();

    return SuccessResponse.OK(data, status, successCode);
  }
}
