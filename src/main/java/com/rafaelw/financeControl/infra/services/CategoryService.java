package com.rafaelw.financeControl.infra.services;

import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.infra.db.entities.CategoryEntity;
import com.rafaelw.financeControl.infra.db.entities.UserEntity;
import com.rafaelw.financeControl.infra.db.repository.JpaCategoryRepository;
import com.rafaelw.financeControl.infra.db.repository.JpaUserRepository;
import com.rafaelw.financeControl.infra.dto.category.CategoryRequestDTO;
import com.rafaelw.financeControl.infra.dto.category.CategoryResponseDTO;
import com.rafaelw.financeControl.infra.dto.category.CategoryUpdateDTO;
import com.rafaelw.financeControl.infra.mappers.CategoryMapper;
import com.rafaelw.financeControl.infra.mappers.UserMapper;
import com.rafaelw.financeControl.infra.services.exceptions.CategoryNotFoundException;
import com.rafaelw.financeControl.infra.services.exceptions.UserNotFoundException;
import com.rafaelw.financeControl.infra.services.factories.CategoryFactory;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

  @Autowired
  private JpaCategoryRepository jpaCategoryRepository;

  @Autowired
  private JpaUserRepository userRepository;

  @Autowired
  private CategoryMapper categoryMapper;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private CategoryFactory categoryFactory;

  @Transactional(readOnly = true)
  public CategoryResponseDTO findById(Long userId, Long categoryId) {
    CategoryEntity categoryEntity = jpaCategoryRepository.findByIdAndUserId(categoryId, userId)
        .orElseThrow(() -> new CategoryNotFoundException(categoryId));

    return categoryMapper.toResponseDTO(categoryEntity);
  }

  @Transactional(readOnly = true)
  public List<CategoryResponseDTO> findAllByUser(Long userId) {
    userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    List<CategoryEntity> categoriesEntityList = jpaCategoryRepository.findAllByUserId(userId);

    return categoriesEntityList.stream()
        .map(entity -> categoryMapper.toCategory(entity))
        .map(category -> categoryMapper.toResponseDTO(category)).toList();

  }

  @Transactional
  public CategoryResponseDTO create(Long userId, CategoryRequestDTO data) {
    UserEntity userEntity = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    User user = userMapper.toUser(userEntity);

    Category category = categoryFactory.create(user, data.name());

    CategoryEntity categoryEntity = categoryMapper.toCategoryEntity(category);

    jpaCategoryRepository.save(categoryEntity);

    return categoryMapper.toResponseDTO(categoryEntity);
  }

  @Transactional
  public CategoryResponseDTO update(Long userId, Long categoryId, CategoryUpdateDTO data) {
    userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    CategoryEntity categoryEntity = jpaCategoryRepository.findByIdAndUserId(categoryId, userId)
        .orElseThrow(() -> new CategoryNotFoundException(categoryId));

    Category category = categoryMapper.toCategory(categoryEntity);

    category.setName(data.name());

    CategoryEntity updatedCategory = categoryMapper.toCategoryEntity(category);
    jpaCategoryRepository.save(updatedCategory);

    return categoryMapper.toResponseDTO(updatedCategory);


  }

}
