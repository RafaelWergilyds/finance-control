package com.rafaelw.financeControl.application.service;

import com.rafaelw.financeControl.application.ports.in.DebitService;
import com.rafaelw.financeControl.application.ports.out.CategoryRepositoryPort;
import com.rafaelw.financeControl.application.ports.out.DebitRepositoryPort;
import com.rafaelw.financeControl.domain.factories.DebitFactory;
import com.rafaelw.financeControl.application.dto.DebitFilterDTO;
import com.rafaelw.financeControl.application.service.exceptions.CategoryNotFoundException;
import com.rafaelw.financeControl.domain.model.entities.Debit;

import java.math.BigDecimal;
import java.util.Optional;

import com.rafaelw.financeControl.infra.outbound.persistence.repository.utils.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DebitServiceImpl implements DebitService {

  @Autowired
  private DebitRepositoryPort debitRepositoryPort;

  @Autowired
  private CategoryRepositoryPort categoryRepositoryPort;

  public DebitServiceImpl(DebitRepositoryPort debitRepositoryPort, CategoryRepositoryPort categoryRepositoryPort){
    this.debitRepositoryPort = debitRepositoryPort;
    this.categoryRepositoryPort = categoryRepositoryPort;
  }

  @Transactional
  public Debit create(Long userId, String name, BigDecimal amount, Long categoryId) {
    Debit debit = DebitFactory.create(userId, name, amount);

    if(categoryId != null){
      addCategoryToDebit(debit, userId, categoryId);
    }

    return debitRepositoryPort.save(debit);
  }

  @Transactional(readOnly = true)
  public Optional<Debit> findById(Long userId, Long id) {
    return debitRepositoryPort.findByIdAndUserId(id, userId);
  }

  @Transactional(readOnly = true)
  public PaginatedResponse<Debit> findAll(Long userId, DebitFilterDTO filter, Integer pageSize, Long cursor) {
    return debitRepositoryPort.findAllByUser(userId, filter, pageSize, cursor);
  }


  @Transactional
  public Optional<Debit> update(Long userId, Long id, String name, BigDecimal amount, Long categoryId) {
    return debitRepositoryPort.findByIdAndUserId(id, userId).map(debit -> {
      if (name != null) {
        debit.changeName(name);
      }
      if (amount != null) {
        debit.changeAmount(amount);
      }
      if (categoryId != null) {
        addCategoryToDebit(debit, userId, categoryId);
      }

      return debitRepositoryPort.save(debit);
    });
  }

  @Transactional
  public void delete(Long userId, Long id) {
    debitRepositoryPort.findByIdAndUserId(id, userId).ifPresent(debit -> {
      debitRepositoryPort.delete(id);
    });
  }

  public BigDecimal getTotalSum(Long userId, DebitFilterDTO filter) {
    return debitRepositoryPort.getTotalSumAmount(userId, filter);
  }

  private void addCategoryToDebit(Debit debit, Long userId, Long categoryId) {
    categoryRepositoryPort.findByIdAndUserId(categoryId, userId
            )
        .orElseThrow(() -> new CategoryNotFoundException(categoryId));

    debit.setCategoryId(categoryId);
  }

}
