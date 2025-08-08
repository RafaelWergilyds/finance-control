package com.rafaelw.financeControl.infra.services;

import com.rafaelw.financeControl.domain.entities.Debit;
import com.rafaelw.financeControl.infra.db.entities.DebitEntity;
import com.rafaelw.financeControl.infra.db.repository.JpaDebitRepository;
import com.rafaelw.financeControl.infra.mappers.DebitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DebitService {

  @Autowired
  private JpaDebitRepository debitRepository;

  @Autowired
  private DebitMapper debitMapper;

  public Debit findById(Long id) {
    DebitEntity debitEntity = debitRepository.findById(id).orElse(new DebitEntity());
    return debitMapper.toDebit(debitEntity);
  }

}
