package com.alexcode.eatgo.application;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.alexcode.eatgo.exceptions.CategoryDuplicationException;
import com.alexcode.eatgo.domain.models.Category;
import com.alexcode.eatgo.domain.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.alexcode.eatgo.network.SuccessResponse;
import com.alexcode.eatgo.interfaces.dto.CategoryRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CategoryServiceTest {

  private CategoryService categoryService;

  @Mock
  private CategoryRepository categoryRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    categoryService = new CategoryService(categoryRepository);
  }

  @Test
  public void getCategories() {
    List<Category> mockCategories = new ArrayList<>();
    mockCategories.add(Category.builder().name("Fast Food").build());

    given(categoryRepository.findAll()).willReturn(mockCategories);

    SuccessResponse response = categoryService.list();

    Category category = (Category) response.getData();
    assertThat(category.getName(), is("Fast Food"));
  }

  @Test
  public void addCategory() {
    SuccessResponse response = categoryService.create(CategoryRequestDto.builder().name("Fast Food").build());
    Category category = (Category) response.getData();
    verify(categoryRepository).save(any());

    assertThat(category.getName(), is("Fast Food"));
  }

  @Test
  public void addCategoryWithExistedData() {
    Category category = Category.builder().name("Fast Food").build();

    given(categoryRepository.findByName("Fast Food"))
            .willReturn(Optional.of(category));

    assertThrows(CategoryDuplicationException.class, () -> {
      categoryService.create(CategoryRequestDto.builder().name("Fast Food").build());
    });
  }
}