package com.rafaelw.financeControl.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import com.rafaelw.financeControl.application.ports.out.CategoryRepositoryPort;
import com.rafaelw.financeControl.domain.factories.CategoryFactory;
import com.rafaelw.financeControl.domain.model.entities.Category;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

class CategoryServiceTest {

  @Mock
  private CategoryRepositoryPort categoryRepositoryPort;

  @Mock
  private CategoryFactory categoryFactory;

  @InjectMocks
  private CategoryServiceImpl service;

  @BeforeEach
  void setup() {
    openMocks(this);
    ReflectionTestUtils.setField(service, "categoryFactory", categoryFactory);
  }

  @Test
  @DisplayName("Should be able to create a category")
  void createCategory() {
    Long userId = 1L;
    Long categoryId = 1L;
    String categoryName = "Food";

    Category domainCategory = Category.builder()
            .id(categoryId)
            .name(categoryName)
            .userId(userId)
            .build();

    when(categoryFactory.create(userId, categoryName)).thenReturn(domainCategory);
    when(categoryRepositoryPort.save(domainCategory)).thenReturn(domainCategory);

    Category response = service.create(userId, categoryName);

    assertThat(response).isNotNull();
    assertThat(response.getName()).isEqualTo(categoryName);

    verify(categoryFactory, times(1)).create(userId, categoryName);
    verify(categoryRepositoryPort, times(1)).save(domainCategory);
  }

  @Test
  @DisplayName("Should be able to find a category by id")
  void findCategoryById() {
    Long userId = 1L;
    Long categoryId = 1L;
    String categoryName = "Food";

    Category domainCategory = Category.builder()
            .id(categoryId)
            .name(categoryName)
            .userId(userId)
            .build();

    when(categoryRepositoryPort.findByIdAndUserId(userId, categoryId)).thenReturn(
        Optional.of(domainCategory));

    Optional<Category> response = service.findById(userId, categoryId);

    assertThat(response).isPresent();
    assertThat(response.get().getName()).isEqualTo(categoryName);

    verify(categoryRepositoryPort, times(1)).findByIdAndUserId(userId, categoryId);
  }

  @Test
  @DisplayName("Should return empty when category not found")
  void findUnexistCategoryById() {
    Long userId = 1L;
    Long categoryId = 1L;

    when(categoryRepositoryPort.findByIdAndUserId(userId, categoryId)).thenReturn(Optional.empty());

    Optional<Category> response = service.findById(userId, categoryId);

    assertThat(response).isEmpty();

    verify(categoryRepositoryPort, times(1)).findByIdAndUserId(userId, categoryId);
  }

  @Test
  @DisplayName("Should be able to find all user categories")
  void findAllCategories() {
    Long userId = 1L;

    Category category1 = Category.builder().id(1L).name("Food").userId(userId).build();
    Category category2 = Category.builder().id(2L).name("Health").userId(userId).build();
    List<Category> categoryList = List.of(category1, category2);

    when(categoryRepositoryPort.findAllByUserId(userId)).thenReturn(categoryList);

    List<Category> response = service.findAll(userId);

    assertThat(response).hasSize(2);
    assertThat(response.get(0).getName()).isEqualTo("Food");
    assertThat(response.get(1).getName()).isEqualTo("Health");

    verify(categoryRepositoryPort, times(1)).findAllByUserId(userId);
  }

  @Test
  @DisplayName("Should be able to update a category")
  void updateCategory() {
    Long userId = 1L;
    Long categoryId = 1L;
    String newName = "Health";

    Category domainCategory = Category.builder()
            .id(categoryId)
            .name("Food")
            .userId(userId)
            .build();

    when(categoryRepositoryPort.findByIdAndUserId(userId, categoryId)).thenReturn(
        Optional.of(domainCategory));
    when(categoryRepositoryPort.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Optional<Category> response = service.update(userId, categoryId, newName);

    assertThat(response).isPresent();
    assertThat(response.get().getName()).isEqualTo(newName);

    verify(categoryRepositoryPort, times(1)).findByIdAndUserId(userId, categoryId);
    verify(categoryRepositoryPort, times(1)).save(any(Category.class));
  }

  @Test
  @DisplayName("Should be able to delete a category")
  void deleteCategory() {
    Long userId = 1L;
    Long categoryId = 1L;

    Category domainCategory = Category.builder()
            .id(categoryId)
            .name("Food")
            .userId(userId)
            .build();

    when(categoryRepositoryPort.findByIdAndUserId(userId, categoryId)).thenReturn(
        Optional.of(domainCategory));
    doNothing().when(categoryRepositoryPort).delete(categoryId);

    service.delete(userId, categoryId);

    verify(categoryRepositoryPort, times(1)).findByIdAndUserId(userId, categoryId);
    verify(categoryRepositoryPort, times(1)).delete(categoryId);
  }
}
