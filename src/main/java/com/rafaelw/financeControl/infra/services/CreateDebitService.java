package com.rafaelw.financeControl.infra.services;

import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.domain.entities.Debit;
import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.domain.service.CreateDebit;
import com.rafaelw.financeControl.infra.db.entities.CategoryEntity;
import com.rafaelw.financeControl.infra.db.entities.DebitEntity;
import com.rafaelw.financeControl.infra.db.entities.UserEntity;
import com.rafaelw.financeControl.infra.db.repository.CategoryRepository;
import com.rafaelw.financeControl.infra.db.repository.JpaDebitRepository;
import com.rafaelw.financeControl.infra.db.repository.UserRepository;
import com.rafaelw.financeControl.infra.dto.debit.CreateDebitDTO;
import com.rafaelw.financeControl.infra.mappers.CategoryMapper;
import com.rafaelw.financeControl.infra.mappers.DebitMapper;
import com.rafaelw.financeControl.infra.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateDebitService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private JpaDebitRepository debitRepository;

    @Autowired
    private CreateDebit createDebit;

    @Autowired
    private DebitMapper debitMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    public DebitEntity execute(CreateDebitDTO data){
      CategoryEntity categoryEntity = categoryRepository.findById(data.categoryId()).orElse(new CategoryEntity());
      UserEntity userEntity = userRepository.findById(data.userId()).orElse(new UserEntity());

      User user = userMapper.toUser(userEntity);
      Category category = categoryMapper.toCategory(categoryEntity);

      Debit debit = createDebit.createDebit(user, category, data.name(), data.amount());

      DebitEntity debitEntity = debitMapper.toDebitEntity(debit);
      debitRepository.save(debitEntity);

      return debitEntity;
    }
}
