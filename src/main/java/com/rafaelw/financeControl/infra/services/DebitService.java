package com.rafaelw.financeControl.infra.services;

import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.domain.entities.Debit;
import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.infra.db.entities.CategoryEntity;
import com.rafaelw.financeControl.infra.db.entities.DebitEntity;
import com.rafaelw.financeControl.infra.db.entities.UserEntity;
import com.rafaelw.financeControl.infra.db.repository.JpaCategoryRepository;
import com.rafaelw.financeControl.infra.db.repository.JpaDebitRepository;
import com.rafaelw.financeControl.infra.db.repository.JpaUserRepository;
import com.rafaelw.financeControl.infra.dto.debit.DebitRequestDTO;
import com.rafaelw.financeControl.infra.dto.debit.DebitResponseDTO;
import com.rafaelw.financeControl.infra.mappers.CategoryMapper;
import com.rafaelw.financeControl.infra.mappers.DebitMapper;
import com.rafaelw.financeControl.infra.mappers.UserMapper;
import com.rafaelw.financeControl.infra.services.exceptions.CategoryNotFoundException;
import com.rafaelw.financeControl.infra.services.exceptions.DebitNotFoundException;
import com.rafaelw.financeControl.infra.services.exceptions.UserNotFoundException;
import com.rafaelw.financeControl.infra.services.factories.DebitFactory;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DebitService {

  @Autowired
  private JpaDebitRepository debitRepository;

  @Autowired
  private JpaUserRepository userRepository;

  @Autowired
  private JpaCategoryRepository categoryRepository;

  @Autowired
  private DebitFactory debitFactory;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private CategoryMapper categoryMapper;

  @Autowired
  private DebitMapper debitMapper;

  public DebitResponseDTO findById(Long userId, Long debitId) {
    DebitEntity debitEntity = debitRepository.findByIdAndUserId(debitId, userId)
        .orElseThrow(() -> new DebitNotFoundException(debitId));
    return debitMapper.toResponse(debitEntity);
  }

  public List<DebitResponseDTO> findAll(Long userId) {
    List<DebitEntity> debitsEntity = debitRepository.findAllByUserId(userId);

    return debitsEntity.stream().map(debitEntity -> debitMapper.toResponse(debitEntity)).toList();
  }

  public DebitResponseDTO create(Long userId, DebitRequestDTO data) {
    UserEntity userEntity = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    User user = userMapper.toUser(userEntity);
    Debit debit = debitFactory.create(user, data.name(), data.amount());

    if (data.categoryId() != null) {
      addCategoryToDebit(debit, userId, data.categoryId());
    }

    DebitEntity debitEntity = debitMapper.toDebitEntity(debit);
    debitRepository.save(debitEntity);

    return debitMapper.toResponse(debitEntity);
  }

  private void addCategoryToDebit(Debit debit, Long userId, Long categoryId) {
    CategoryEntity categoryEntity = categoryRepository.findByIdAndUserId(categoryId,
            userId)
        .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    Category category = categoryMapper.toCategory(categoryEntity);
    debit.setCategory(category);
  }

}
