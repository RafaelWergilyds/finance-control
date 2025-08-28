package com.rafaelw.financeControl.application.services;

import com.rafaelw.financeControl.application.dto.debit.DebitFilterDTO;
import com.rafaelw.financeControl.application.dto.debit.DebitRequestDTO;
import com.rafaelw.financeControl.application.dto.debit.DebitResponseDTO;
import com.rafaelw.financeControl.application.dto.debit.DebitUpdateDTO;
import com.rafaelw.financeControl.application.dto.debit.TotalDebitsResponse;
import com.rafaelw.financeControl.application.mappers.CategoryMapper;
import com.rafaelw.financeControl.application.mappers.DebitMapper;
import com.rafaelw.financeControl.application.mappers.UserMapper;
import com.rafaelw.financeControl.application.services.exceptions.CategoryNotFoundException;
import com.rafaelw.financeControl.application.services.exceptions.DebitNotFoundException;
import com.rafaelw.financeControl.application.services.exceptions.UserNotFoundException;
import com.rafaelw.financeControl.application.utils.PaginatedResponse;
import com.rafaelw.financeControl.application.utils.Pagination;
import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.domain.entities.Debit;
import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.domain.factories.DebitFactory;
import com.rafaelw.financeControl.domain.service.GetTotalSumDebits;
import com.rafaelw.financeControl.infra.persist.entities.CategoryPersist;
import com.rafaelw.financeControl.infra.persist.entities.DebitPersist;
import com.rafaelw.financeControl.infra.persist.entities.UserPersist;
import com.rafaelw.financeControl.infra.persist.repository.JpaCategoryRepository;
import com.rafaelw.financeControl.infra.persist.repository.JpaDebitRepository;
import com.rafaelw.financeControl.infra.persist.repository.JpaUserRepository;
import com.rafaelw.financeControl.infra.persist.repository.specifications.DebitSpecification;
import com.rafaelw.financeControl.infra.persist.repository.specifications.SpecificationUtil;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DebitService {

  @Autowired
  private GetTotalSumDebits getTotalSumDebits;

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

  private static Specification<DebitPersist> filterDebit(Long userId, DebitFilterDTO filter) {
    Specification<DebitPersist> spec = SpecificationUtil.empty();
    spec = spec.and(DebitSpecification.findByUserId(userId));

    if (filter.categoryId() != null) {
      spec = spec.and(DebitSpecification.findByCategoryId(filter.categoryId()));
    }
    if (filter.categoryName() != null) {
      spec = spec.and(DebitSpecification.findByCategoryName(filter.categoryName()));
    }
    if (filter.minAmount() != null && filter.minAmount().compareTo(BigDecimal.ZERO) >= 0) {
      spec = spec.and(
          DebitSpecification.hasAmountGreaterThanOrEqualsTo(filter.minAmount()));
    }
    if (filter.maxAmount() != null && filter.maxAmount().compareTo(BigDecimal.ZERO) >= 0) {
      spec = spec.and(DebitSpecification.hasAmountLessThanEqualsTo(filter.maxAmount()));
    }
    if (filter.until() != null) {
      LocalDate localDate = LocalDate.parse(filter.until());
      ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneOffset.UTC);
      Instant moment = zonedDateTime.toInstant();
      spec = spec.and(DebitSpecification.findPreviousDebitMoment(moment));
    }
    if (filter.since() != null) {
      LocalDate localDate = LocalDate.parse(filter.since());
      ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneOffset.UTC);
      Instant moment = zonedDateTime.toInstant();
      spec = spec.and(DebitSpecification.findNextDebitMoment(moment));
    }
    return spec;
  }

  @Transactional(readOnly = true)
  public DebitResponseDTO findById(Long userId, Long debitId) {
    DebitPersist debitPersist = debitRepository.findByIdAndUserId(debitId, userId)
        .orElseThrow(() -> new DebitNotFoundException(debitId));
    return debitMapper.toResponse(debitPersist);
  }

  @Transactional(readOnly = true)
  public PaginatedResponse<DebitResponseDTO> findAll(Long userId, DebitFilterDTO filter,
      Integer pageSize, Long cursor) {
    userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    Specification<DebitPersist> spec = filterDebit(userId, filter);

    return Pagination.paginate(debitRepository,
        spec, pageSize, cursor, debitMapper::toResponse, "id");
  }

  @Transactional
  public DebitResponseDTO create(Long userId, DebitRequestDTO data) {
    UserPersist userPersist = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    User user = userMapper.toDomain(userPersist);
    Debit debit = debitFactory.create(user, data.name(), data.amount());

    if (data.categoryId() != null) {
      addCategoryToDebit(debit, userId, data.categoryId());
    }

    DebitPersist debitPersist = debitMapper.toPersist(debit);
    debitRepository.save(debitPersist);

    return debitMapper.toResponse(debitPersist);
  }

  @Transactional
  public DebitResponseDTO update(Long userId, Long debitId, DebitUpdateDTO data) {
    userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    DebitPersist debitPersist = debitRepository.findByIdAndUserId(debitId, userId)
        .orElseThrow(() -> new DebitNotFoundException(debitId));

    Debit debit = debitMapper.toDomain(debitPersist);

    if (data.name() != null) {
      debit.setName(data.name());
    }
    if (data.amount() != null) {
      debit.setAmount(data.amount());
    }
    if (data.categoryId() != null) {
      addCategoryToDebit(debit, userId, data.categoryId());
    }

    DebitPersist debitUpdated = debitMapper.toPersist(debit);
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

  public TotalDebitsResponse getTotalSum(Long userId, DebitFilterDTO filter) {
    userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    Specification<DebitPersist> debitSpec = filterDebit(userId, filter);

    List<DebitPersist> debitPersists = debitRepository.findAll(debitSpec);
    List<Debit> debits = debitPersists.stream()
        .map(debitPersist -> debitMapper.toDomain(debitPersist)).toList();

    return new TotalDebitsResponse(getTotalSumDebits.execute(debits));
  }

  private void addCategoryToDebit(Debit debit, Long userId, Long categoryId) {
    CategoryPersist categoryPersist = categoryRepository.findByIdAndUserId(categoryId,
            userId)
        .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    Category category = categoryMapper.toDomain(categoryPersist);
    debit.setCategory(category);
  }

}
