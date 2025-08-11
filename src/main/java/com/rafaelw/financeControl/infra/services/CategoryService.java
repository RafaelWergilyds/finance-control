package com.rafaelw.financeControl.infra.services;

import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.domain.factories.CategoryFactory;
import com.rafaelw.financeControl.infra.db.entities.CategoryPersist;
import com.rafaelw.financeControl.infra.db.entities.DebitPersist;
import com.rafaelw.financeControl.infra.db.entities.UserPersist;
import com.rafaelw.financeControl.infra.db.repository.JpaCategoryRepository;
import com.rafaelw.financeControl.infra.db.repository.JpaDebitRepository;
import com.rafaelw.financeControl.infra.db.repository.JpaUserRepository;
import com.rafaelw.financeControl.infra.dto.category.CategoryRequestDTO;
import com.rafaelw.financeControl.infra.dto.category.CategoryResponseDTO;
import com.rafaelw.financeControl.infra.dto.category.CategoryUpdateDTO;
import com.rafaelw.financeControl.infra.mappers.AvoidContext;
import com.rafaelw.financeControl.infra.mappers.CategoryMapper;
import com.rafaelw.financeControl.infra.mappers.DebitMapper;
import com.rafaelw.financeControl.infra.mappers.UserMapper;
import com.rafaelw.financeControl.infra.services.exceptions.CategoryNotFoundException;
import com.rafaelw.financeControl.infra.services.exceptions.UserNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

  @Autowired
  private JpaDebitRepository jpaDebitRepository;

  @Autowired
  private JpaCategoryRepository jpaCategoryRepository;

  @Autowired
  private JpaUserRepository userRepository;

  @Autowired
  private CategoryMapper categoryMapper;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private DebitMapper debitMapper;

  @Autowired
  private CategoryFactory categoryFactory;

  @Transactional(readOnly = true)
  public CategoryResponseDTO findById(Long userId, Long categoryId) {
    CategoryPersist categoryPersist = jpaCategoryRepository.findByIdAndUserId(categoryId, userId)
        .orElseThrow(() -> new CategoryNotFoundException(categoryId));

    return categoryMapper.toResponseDTO(categoryPersist);
  }

  @Transactional(readOnly = true)
  public List<CategoryResponseDTO> findAllByUser(Long userId) {
    userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    List<CategoryPersist> categoriesPersistList = jpaCategoryRepository.findAllByUserId(userId);

    return categoriesPersistList.stream()
        .map(categoryPersist -> categoryMapper.toDomain(categoryPersist, new AvoidContext()))
        .map(category -> categoryMapper.toResponseDTO(category)).toList();

  }

  @Transactional
  public CategoryResponseDTO create(Long userId, CategoryRequestDTO data) {
    UserPersist userPersist = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    User user = userMapper.toDomain(userPersist, new AvoidContext());
    Category category = categoryFactory.create(user, data.name());
    CategoryPersist categoryPersist = categoryMapper.toPersist(category, new AvoidContext());

    jpaCategoryRepository.save(categoryPersist);

    return categoryMapper.toResponseDTO(categoryPersist);
  }

  @Transactional
  public CategoryResponseDTO update(Long userId, Long categoryId, CategoryUpdateDTO data) {
    userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));
    CategoryPersist categoryPersist = jpaCategoryRepository.findByIdAndUserId(categoryId, userId)
        .orElseThrow(() -> new CategoryNotFoundException(categoryId));

    Category category = categoryMapper.toDomain(categoryPersist, new AvoidContext());
    category.setName(data.name());

    CategoryPersist updatedCategory = categoryMapper.toPersist(category, new AvoidContext());
    jpaCategoryRepository.save(updatedCategory);

    return categoryMapper.toResponseDTO(updatedCategory);


  }

  @Transactional
  public void delete(Long userId, Long categoryId) {
    jpaCategoryRepository.findByIdAndUserId(categoryId, userId)
        .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    List<DebitPersist> associatedDebits = jpaDebitRepository.findAllByCategoryId(categoryId);

    for (DebitPersist debit : associatedDebits) {
      debit.setCategory(null);
    }

    jpaCategoryRepository.deleteById(categoryId);
  }

}
