package com.rafaelw.financeControl.infra.services;

import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.domain.entities.Debit;
import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.domain.factories.DebitFactory;
import com.rafaelw.financeControl.infra.db.entities.CategoryPersist;
import com.rafaelw.financeControl.infra.db.entities.DebitPersist;
import com.rafaelw.financeControl.infra.db.entities.UserPersist;
import com.rafaelw.financeControl.infra.db.repository.JpaCategoryRepository;
import com.rafaelw.financeControl.infra.db.repository.JpaDebitRepository;
import com.rafaelw.financeControl.infra.db.repository.JpaUserRepository;
import com.rafaelw.financeControl.infra.dto.debit.DebitRequestDTO;
import com.rafaelw.financeControl.infra.dto.debit.DebitResponseDTO;
import com.rafaelw.financeControl.infra.dto.debit.DebitUpdateDTO;
import com.rafaelw.financeControl.infra.mappers.AvoidContext;
import com.rafaelw.financeControl.infra.mappers.CategoryMapper;
import com.rafaelw.financeControl.infra.mappers.DebitMapper;
import com.rafaelw.financeControl.infra.mappers.UserMapper;
import com.rafaelw.financeControl.infra.services.exceptions.CategoryNotFoundException;
import com.rafaelw.financeControl.infra.services.exceptions.DebitNotFoundException;
import com.rafaelw.financeControl.infra.services.exceptions.UserNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional(readOnly = true)
  public DebitResponseDTO findById(Long userId, Long debitId) {
    DebitPersist debitPersist = debitRepository.findByIdAndUserId(debitId, userId)
        .orElseThrow(() -> new DebitNotFoundException(debitId));
    return debitMapper.toResponse(debitPersist);
  }

  @Transactional(readOnly = true)
  public List<DebitResponseDTO> findAll(Long userId) {
    List<DebitPersist> debitsPersist = debitRepository.findAllByUserId(userId);

    return debitsPersist.stream().map(debitPersist -> debitMapper.toResponse(debitPersist))
        .toList();
  }

  @Transactional
  public DebitResponseDTO create(Long userId, DebitRequestDTO data) {
    UserPersist userPersist = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    User user = userMapper.toDomain(userPersist, new AvoidContext());
    Debit debit = debitFactory.create(user, data.name(), data.amount());

    if (data.categoryId() != null) {
      addCategoryToDebit(debit, userId, data.categoryId());
    }

    DebitPersist debitPersist = debitMapper.toPersist(debit, new AvoidContext());
    debitRepository.save(debitPersist);

    return debitMapper.toResponse(debitPersist);
  }

  @Transactional
  public DebitResponseDTO update(Long userId, Long debitId, DebitUpdateDTO data) {
    userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    DebitPersist debitPersist = debitRepository.findByIdAndUserId(debitId, userId)
        .orElseThrow(() -> new DebitNotFoundException(debitId));

    Debit debit = debitMapper.toDomain(debitPersist, new AvoidContext());

    if (data.name() != null) {
      debit.setName(data.name());
    }
    if (data.amount() != null) {
      debit.setAmount(data.amount());
    }
    if (data.categoryId() != null) {
      addCategoryToDebit(debit, userId, data.categoryId());
    }

    DebitPersist debitUpdated = debitMapper.toPersist(debit, new AvoidContext());
    debitRepository.save(debitUpdated);

    return debitMapper.toResponse(debitUpdated);

  }

  @Transactional
  public void delete(Long userId, Long debitId) {
    userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));
    DebitPersist debitPersist = debitRepository.findByIdAndUserId(debitId, userId)
        .orElseThrow(() -> new DebitNotFoundException(debitId));

    debitPersist.setCategory(null);

    debitRepository.deleteById(debitId);
  }

  public BigDecimal getTotalSum(Long userId) {
    UserPersist userPersist = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    User user = userMapper.toDomain(userPersist, new AvoidContext());

    return user.getTotalSumDebits();
  }

  private void addCategoryToDebit(Debit debit, Long userId, Long categoryId) {
    CategoryPersist categoryPersist = categoryRepository.findByIdAndUserId(categoryId,
            userId)
        .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    Category category = categoryMapper.toDomain(categoryPersist, new AvoidContext());
    debit.setCategory(category);
  }

}
