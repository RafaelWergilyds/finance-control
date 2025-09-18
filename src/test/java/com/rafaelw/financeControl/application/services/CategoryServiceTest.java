package com.rafaelw.financeControl.application.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import com.rafaelw.financeControl.application.dto.category.CategoryRequestDTO;
import com.rafaelw.financeControl.application.dto.category.CategoryResponseDTO;
import com.rafaelw.financeControl.application.dto.category.CategoryUpdateDTO;
import com.rafaelw.financeControl.application.mappers.CategoryMapper;
import com.rafaelw.financeControl.application.mappers.DebitMapper;
import com.rafaelw.financeControl.application.mappers.UserMapper;
import com.rafaelw.financeControl.application.services.exceptions.CategoryNotFoundException;
import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.domain.entities.enums.Role;
import com.rafaelw.financeControl.domain.factories.CategoryFactory;
import com.rafaelw.financeControl.infra.persist.entities.CategoryPersist;
import com.rafaelw.financeControl.infra.persist.entities.UserPersist;
import com.rafaelw.financeControl.infra.persist.repository.JpaCategoryRepository;
import com.rafaelw.financeControl.infra.persist.repository.JpaDebitRepository;
import com.rafaelw.financeControl.infra.persist.repository.JpaUserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class CategoryServiceTest {

  @Mock
  private JpaDebitRepository debitRepository;

  @Mock
  private JpaCategoryRepository categoryRepository;

  @Mock
  private JpaUserRepository userRepository;

  @Mock
  private CategoryMapper categoryMapper;

  @Mock
  private UserMapper userMapper;

  @Mock
  private DebitMapper debitMapper;

  @Mock
  private CategoryFactory categoryFactory;

  @InjectMocks
  private CategoryService service;

  @BeforeEach
  void setup() {
    openMocks(this);
  }

  @Test
  @DisplayName("Should be able to create a category")
  void createCategory() {
    Long userId = 1L;
    Long categoryId = 1L;
    String categoryName = "Food";

    UserPersist userPersist = new UserPersist(userId, "Joel", "joel@gmail.com", "hashedPassword",
        true,
        Role.COMMON, null, null);
    User domainUser = new User(userId, "Joel", "joel@gmail.com", "hashedPassword", true,
        Role.COMMON, null, null);

    CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO(categoryName);
    Category domainCategory = new Category(categoryId, categoryName, domainUser, null);
    CategoryPersist categoryPersist = new CategoryPersist(categoryId, categoryName, userPersist,
        null);
    CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(categoryId, categoryName);

    when(userRepository.findById(userId)).thenReturn(Optional.of(userPersist));
    when(userMapper.toDomain(userPersist)).thenReturn(domainUser);
    when(categoryFactory.create(domainUser, categoryName)).thenReturn(domainCategory);
    when(categoryMapper.toPersist(domainCategory)).thenReturn(categoryPersist);
    when(categoryRepository.save(categoryPersist)).thenReturn(categoryPersist);
    when(categoryMapper.toResponseDTO(categoryPersist)).thenReturn(categoryResponseDTO);

    CategoryResponseDTO response = service.create(userId, categoryRequestDTO);

    assertThat(response).isNotNull();
    assertThat(response.name()).isEqualTo(categoryName);

    verify(userRepository, times(1)).findById(userId);
    verify(userMapper, times(1)).toDomain(any(UserPersist.class));
    verify(categoryFactory, times(1)).create(any(), any());
    verify(categoryMapper, times(1)).toPersist(any(Category.class));
    verify(categoryRepository, times(1)).save(any(CategoryPersist.class));
    verify(categoryMapper, times(1)).toResponseDTO(any(CategoryPersist.class));


  }

  @Test
  @DisplayName("Should be able to find a category by id")
  void findCategoryById() {
    Long userId = 1L;
    Long categoryId = 1L;

    UserPersist userPersist = new UserPersist(userId, "Joel", "joel@gmail.com", "hashedPassword",
        true,
        Role.COMMON, null, null);

    CategoryPersist categoryPersist = new CategoryPersist(categoryId, "Food", userPersist,
        null);
    CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(categoryId, "Food");

    when(categoryRepository.findByIdAndUserId(userId, categoryId)).thenReturn(
        Optional.of(categoryPersist));
    when(categoryMapper.toResponseDTO(categoryPersist)).thenReturn(categoryResponseDTO);

    CategoryResponseDTO response = service.findById(userId, categoryId);

    assertThat(response).isNotNull();
    assertThat(response.name()).isEqualTo("Food");

    verify(categoryRepository, times(1)).findByIdAndUserId(userId, categoryId);
    verify(categoryMapper, times(1)).toResponseDTO(categoryPersist);

  }

  @Test
  @DisplayName("Should not be able to find a category by id with unexist category")
  void findUnexistCategoryById() {
    Long userId = 1L;
    Long categoryId = 1L;

    when(categoryRepository.findByIdAndUserId(userId, categoryId)).thenThrow(
        new CategoryNotFoundException(categoryId));

    assertThatThrownBy(() -> {
      service.findById(userId, categoryId);
    }).isInstanceOf(CategoryNotFoundException.class).hasMessage("Category with id 1 not found");

    verify(categoryRepository, times(1)).findByIdAndUserId(userId, categoryId);

  }

  @Test
  @DisplayName("Should be able to find all user categories")
  void findAllCategories() {
    Long userId = 1L;

    UserPersist userPersist = new UserPersist(userId, "Joel", "joel@gmail.com", "hashedPassword",
        true,
        Role.COMMON, null, null);

    CategoryPersist category1 = new CategoryPersist(1L, "Food", userPersist, null);
    CategoryPersist category2 = new CategoryPersist(2L, "Health", userPersist, null);
    List<CategoryPersist> categoryList = List.of(category1, category2);

    CategoryResponseDTO categoryResponseDTO1 = new CategoryResponseDTO(1L, "Food");
    CategoryResponseDTO categoryResponseDTO2 = new CategoryResponseDTO(2L, "Health");

    when(userRepository.findById(userId)).thenReturn(Optional.of(userPersist));
    when(categoryRepository.findAllByUserId(userId)).thenReturn(categoryList);
    when(categoryMapper.toResponseDTO(category1)).thenReturn(categoryResponseDTO1);
    when(categoryMapper.toResponseDTO(category2)).thenReturn(categoryResponseDTO2);

    List<CategoryResponseDTO> response = service.findAll(userId);

    assertThat(response.size()).isEqualTo(2);
    assertThat(response.get(0).name()).isEqualTo("Food");
    assertThat(response.get(1).name()).isEqualTo("Health");

    verify(userRepository, times(1)).findById(userId);
    verify(categoryRepository, times(1)).findAllByUserId(userId);
    verify(categoryMapper, times(2)).toResponseDTO(any(CategoryPersist.class));

  }

  @Test
  @DisplayName("Should be able to update a category")
  void updateCategory() {
    Long userId = 1L;
    Long categoryId = 1L;

    UserPersist userPersist = new UserPersist(userId, "Joel", "joel@gmail.com", "hashedPassword",
        true,
        Role.COMMON, null, null);
    User domainUser = new User(userId, "Joel", "joel@gmail.com", "hashedPassword",
        true,
        Role.COMMON, null, null);

    CategoryUpdateDTO categoryUpdateDTO = new CategoryUpdateDTO("Health");
    CategoryPersist categoryPersist = new CategoryPersist(categoryId, "Food", userPersist, null);
    Category domainCategory = new Category(categoryId, "Food", domainUser, null);
    CategoryPersist updatedCategory = new CategoryPersist(categoryId, "Health", userPersist, null);
    CategoryResponseDTO categoryUpdatedResponse = new CategoryResponseDTO(categoryId, "Health");

    when(userRepository.findById(userId)).thenReturn(Optional.of(userPersist));
    when(categoryRepository.findByIdAndUserId(categoryId, userId)).thenReturn(
        Optional.of(categoryPersist));
    when(categoryMapper.toDomain(categoryPersist)).thenReturn(domainCategory);
    when(categoryMapper.toPersist(domainCategory)).thenReturn(updatedCategory);
    when(categoryRepository.save(updatedCategory)).thenReturn(updatedCategory);
    when(categoryMapper.toResponseDTO(updatedCategory)).thenReturn(categoryUpdatedResponse);

    CategoryResponseDTO response = service.update(userId, categoryId, categoryUpdateDTO);

    assertThat(response).isNotNull();
    assertThat(response.name()).isEqualTo("Health");

    verify(userRepository, times(1)).findById(userId);
    verify(categoryRepository, times(1)).findByIdAndUserId(userId, categoryId);
    verify(categoryMapper, times(1)).toDomain(any(CategoryPersist.class));
    verify(categoryMapper, times(1)).toPersist(any(Category.class));
    verify(categoryRepository, times(1)).save(any(CategoryPersist.class));
    verify(categoryMapper, times(1)).toResponseDTO(any(CategoryPersist.class));

  }

  @Test
  @DisplayName("Should be able to delete a category")
  void deleteCategory() {
    Long userId = 1L;
    Long categoryId = 1L;

    UserPersist userPersist = new UserPersist(userId, "Joel", "joel@gmail.com", "hashedPassword",
        true,
        Role.COMMON, null, null);

    CategoryPersist categoryPersist = new CategoryPersist(categoryId, "Food", userPersist, null);

    when(categoryRepository.findByIdAndUserId(categoryId, userId)).thenReturn(
        Optional.of(categoryPersist));
    doNothing().when(categoryRepository).deleteById(categoryId);

    service.delete(userId, categoryId);

    verify(categoryRepository, times(1)).findByIdAndUserId(categoryId, userId);
    verify(categoryRepository, times(1)).deleteById(categoryId);
  }

  @Test
  @DisplayName("Should not be able to delete a unexist category")
  void deleteUnexistCategory() {
    Long userId = 1L;
    Long categoryId = 1L;

    UserPersist userPersist = new UserPersist(1L, "Joel", "joel@gmail.com", "hashedPassword",
        true,
        Role.COMMON, null, null);

    when(categoryRepository.findByIdAndUserId(categoryId, userId)).thenThrow(
        new CategoryNotFoundException(categoryId));

    doNothing().when(categoryRepository).deleteById(categoryId);

    assertThatThrownBy(() -> {
      service.delete(userId, categoryId);
    }).isInstanceOf(CategoryNotFoundException.class).hasMessage("Category with id 1 not found");

    verify(categoryRepository, times(1)).findByIdAndUserId(categoryId, userId);
  }

}