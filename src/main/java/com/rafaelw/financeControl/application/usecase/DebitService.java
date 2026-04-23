package com.rafaelw.financeControl.application.usecase;

import com.rafaelw.financeControl.infra.inbound.rest.dto.debit.DebitFilterDTO;
import com.rafaelw.financeControl.infra.inbound.rest.dto.debit.DebitRequestDTO;
import com.rafaelw.financeControl.infra.inbound.rest.dto.debit.DebitResponseDTO;
import com.rafaelw.financeControl.infra.inbound.rest.dto.debit.DebitUpdateDTO;
import com.rafaelw.financeControl.infra.inbound.rest.dto.debit.TotalDebitsResponse;
import com.rafaelw.financeControl.application.mappers.CategoryMapper;
import com.rafaelw.financeControl.application.mappers.DebitMapper;
import com.rafaelw.financeControl.application.mappers.UserMapper;
import com.rafaelw.financeControl.application.usecase.exceptions.CategoryNotFoundException;
import com.rafaelw.financeControl.application.usecase.exceptions.DebitNotFoundException;
import com.rafaelw.financeControl.application.usecase.exceptions.UserNotFoundException;
import com.rafaelw.financeControl.application.utils.PaginatedResponse;
import com.rafaelw.financeControl.application.utils.Pagination;
import com.rafaelw.financeControl.domain.model.entities.Category;
import com.rafaelw.financeControl.domain.model.entities.Debit;
import com.rafaelw.financeControl.domain.model.entities.User;
import com.rafaelw.financeControl.domain.factories.DebitFactory;
import com.rafaelw.financeControl.domain.services.GetTotalSumDebits;
import com.rafaelw.financeControl.infra.outbound.persistence.entities.CategoryPersist;
import com.rafaelw.financeControl.infra.outbound.persistence.entities.DebitPersist;
import com.rafaelw.financeControl.infra.outbound.persistence.entities.UserPersist;
import com.rafaelw.financeControl.infra.outbound.persistence.repository.JpaCategoryRepository;
import com.rafaelw.financeControl.infra.outbound.persistence.repository.JpaDebitRepository;
import com.rafaelw.financeControl.infra.outbound.persistence.repository.JpaUserRepository;
import com.rafaelw.financeControl.infra.outbound.persistence.repository.specifications.DebitSpecification;

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

    Specification<DebitPersist> spec = (root, query, cb) ->
            cb.equal(root.get("user").get("id"), userId);

    spec = spec.and(DebitSpecification.addFilter(filter));

    return Pagination.paginate(debitRepository,
        spec, pageSize, cursor, debitMapper::toResponse, "id");
  }

  @Transactional
  public DebitResponseDTO create(Long userId, DebitRequestDTO data) {
    UserPersist userPersist = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    User user = userMapper.toDomain(userPersist);
    Debit debit = debitFactory.create(user.getId(), data.name(), data.amount());

    if (data.categoryId() != null) {
      addCategoryToDebit(debit, userId, data.categoryId());
    }

    DebitPersist debitPersist = debitMapper.toPersist(debit);
    DebitPersist savedDebit = debitRepository.save(debitPersist);

    return debitMapper.toResponse(savedDebit);
  }

  @Transactional
  public DebitResponseDTO update(Long userId, Long debitId, DebitUpdateDTO data) {
    userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    DebitPersist debitPersist = debitRepository.findByIdAndUserId(debitId, userId)
        .orElseThrow(() -> new DebitNotFoundException(debitId));

    Debit debit = debitMapper.toDomain(debitPersist);

    if (data.name() != null) {
      debit.changeName(data.name());
    }
    if (data.amount() != null) {
      debit.changeAmount(data.amount());
    }
    if (data.categoryId() != null) {
      addCategoryToDebit(debit, userId, data.categoryId());
    }

    DebitPersist debitUpdated = debitMapper.toPersist(debit);
    DebitPersist savedUpdated = debitRepository.save(debitUpdated);

    return debitMapper.toResponse(savedUpdated);

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

    Specification<DebitPersist> spec = (root, query, cb) ->
            cb.equal(root.get("user").get("id"), userId);

    spec = spec.and(DebitSpecification.addFilter(filter));

    List<DebitPersist> debitPersists = debitRepository.findAll(spec);
    List<Debit> debits = debitPersists.stream()
        .map(debitPersist -> debitMapper.toDomain(debitPersist)).toList();

    return new TotalDebitsResponse(getTotalSumDebits.execute(debits));
  }

  private void addCategoryToDebit(Debit debit, Long userId, Long categoryId) {
    CategoryPersist categoryPersist = categoryRepository.findByIdAndUserId(categoryId,
            userId)
        .orElseThrow(() -> new CategoryNotFoundException(categoryId));

    Category category = categoryMapper.toDomain(categoryPersist);
    debit.setCategoryId(category.getId());
  }

}
