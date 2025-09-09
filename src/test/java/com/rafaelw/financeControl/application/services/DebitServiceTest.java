package com.rafaelw.financeControl.application.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rafaelw.financeControl.application.dto.debit.DebitRequestDTO;
import com.rafaelw.financeControl.application.dto.debit.DebitResponseDTO;
import com.rafaelw.financeControl.application.mappers.CategoryMapper;
import com.rafaelw.financeControl.application.mappers.DebitMapper;
import com.rafaelw.financeControl.application.mappers.UserMapper;
import com.rafaelw.financeControl.application.services.exceptions.CategoryNotFoundException;
import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.domain.entities.Debit;
import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.domain.entities.enums.Role;
import com.rafaelw.financeControl.domain.factories.DebitFactory;
import com.rafaelw.financeControl.domain.service.GetTotalSumDebits;
import com.rafaelw.financeControl.infra.persist.entities.CategoryPersist;
import com.rafaelw.financeControl.infra.persist.entities.DebitPersist;
import com.rafaelw.financeControl.infra.persist.entities.UserPersist;
import com.rafaelw.financeControl.infra.persist.repository.JpaCategoryRepository;
import com.rafaelw.financeControl.infra.persist.repository.JpaDebitRepository;
import com.rafaelw.financeControl.infra.persist.repository.JpaUserRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class DebitServiceTest {

  @Mock
  private GetTotalSumDebits getTotalSumDebits;

  @Mock
  private JpaDebitRepository debitRepository;

  @Mock
  private JpaUserRepository userRepository;

  @Mock
  private JpaCategoryRepository categoryRepository;

  @Mock
  private DebitFactory debitFactory;

  @Mock
  private UserMapper userMapper;

  @Mock
  private CategoryMapper categoryMapper;

  @Mock
  private DebitMapper debitMapper;

  @InjectMocks
  private DebitService service;


  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should be able to create a debit")
  void createDebit() {
    Long userId = 1L;
    Instant moment = Instant.parse("2025-10-10T10:00:00Z");

    DebitRequestDTO debitRequest = new DebitRequestDTO("Pizza", BigDecimal.valueOf(50.00), null);
    UserPersist userPersist = new UserPersist(1L, "Joel", "joel@gmail.com", "hashedPassword", true,
        Role.COMMON, null, null);
    User domainUser = new User(1L, "Joel", "joel@gmail.com", "hashedPassword", true,
        Role.COMMON, null, null);
    Debit createdDebit = new Debit(domainUser, "Pizza", BigDecimal.valueOf(50.00));
    DebitPersist debitPersist = new DebitPersist(1L, "Pizza", BigDecimal.valueOf(50.00),
        moment, userPersist, null);
    DebitResponseDTO debitResponse = new DebitResponseDTO(1L, "Pizza", BigDecimal.valueOf(50.00),
        moment, null);

    when(userRepository.findById(userId)).thenReturn(Optional.of(userPersist));
    when(userMapper.toDomain(userPersist)).thenReturn(domainUser);
    when(debitFactory.create(domainUser, debitRequest.name(), debitRequest.amount())).thenReturn(
        createdDebit);
    when(debitMapper.toPersist(createdDebit)).thenReturn(debitPersist);
    when(debitRepository.save(debitPersist)).thenReturn(debitPersist);
    when(debitMapper.toResponse(debitPersist)).thenReturn(debitResponse);

    DebitResponseDTO response = service.create(userId, debitRequest);

    assertThat(response.id()).isEqualTo(1L);
    assertThat(response.name()).isEqualTo("Pizza");
    assertThat(response.amount()).isEqualTo(BigDecimal.valueOf(50.00));
    assertThat(response.moment()).isEqualTo(moment);
    assertThat(response.categoryId()).isNull();

    verify(userRepository, times(1)).findById(userId);
    verify(userMapper, times(1)).toDomain(any(UserPersist.class));
    verify(debitFactory, times(1)).create(any(), any(), any());
    verify(debitMapper, times(1)).toPersist(any(Debit.class));
    verify(debitMapper, times(1)).toResponse(any(DebitPersist.class));
    verify(debitRepository, times(1)).save(any(DebitPersist.class));

  }

  @Test
  @DisplayName("Should be able to create a debit with a category")
  void createDebitWithCategory() {
    Long userId = 1L;
    Long categoryId = 1L;
    Long debitId = 1L;
    Instant moment = Instant.parse("2025-10-10T10:00:00Z");

    DebitRequestDTO debitRequest = new DebitRequestDTO("Pizza", BigDecimal.valueOf(50.00),
        categoryId);
    UserPersist userPersist = new UserPersist(userId, "Joel", "joel@gmail.com", "hashedPassword",
        true,
        Role.COMMON, null, null);
    User domainUser = new User(userId, "Joel", "joel@gmail.com", "hashedPassword", true,
        Role.COMMON, null, null);

    CategoryPersist categoryPersist = new CategoryPersist(categoryId, "Food", userPersist, null);
    Category domainCategory = new Category(categoryId, "Food", domainUser, null);

    userPersist.setCategories(Set.of(categoryPersist));
    domainUser.setCategories(Set.of(domainCategory));

    Debit createdDebit = new Debit(domainUser, "Pizza", BigDecimal.valueOf(50.00), domainCategory);
    DebitPersist debitPersist = new DebitPersist(debitId, "Pizza", BigDecimal.valueOf(50.00),
        moment, userPersist, categoryPersist);
    DebitResponseDTO debitResponse = new DebitResponseDTO(debitId, "Pizza",
        BigDecimal.valueOf(50.00),
        moment, categoryId);

    when(userRepository.findById(userId)).thenReturn(Optional.of(userPersist));
    when(userMapper.toDomain(userPersist)).thenReturn(domainUser);
    when(debitFactory.create(domainUser, debitRequest.name(), debitRequest.amount())).thenReturn(
        createdDebit);
    when(categoryRepository.findByIdAndUserId(categoryId, userId)).thenReturn(
        Optional.of(categoryPersist));
    when(debitMapper.toPersist(createdDebit)).thenReturn(debitPersist);
    when(debitRepository.save(debitPersist)).thenReturn(debitPersist);
    when(debitMapper.toResponse(debitPersist)).thenReturn(debitResponse);

    DebitResponseDTO response = service.create(userId, debitRequest);

    assertThat(response.id()).isEqualTo(debitId);
    assertThat(response.name()).isEqualTo("Pizza");
    assertThat(response.amount()).isEqualTo(BigDecimal.valueOf(50.00));
    assertThat(response.moment()).isEqualTo(moment);
    assertThat(response.categoryId()).isEqualTo(categoryId);

    verify(userRepository, times(1)).findById(userId);
    verify(userMapper, times(1)).toDomain(any(UserPersist.class));
    verify(debitFactory, times(1)).create(any(), any(), any());
    verify(categoryRepository, times(1)).findByIdAndUserId(any(), any());
    verify(debitMapper, times(1)).toPersist(any(Debit.class));
    verify(debitMapper, times(1)).toResponse(any(DebitPersist.class));
    verify(debitRepository, times(1)).save(any(DebitPersist.class));

  }

  @Test
  @DisplayName("Should not be able to create a debit with a unexist category")
  void createDebitWithUnexistCategory() {
    Long userId = 1L;
    Long categoryId = 1L;

    DebitRequestDTO debitRequest = new DebitRequestDTO("Pizza", BigDecimal.valueOf(50.00),
        categoryId);

    UserPersist userPersist = new UserPersist(userId, "Joel", "joel@gmail.com", "hashedPassword",
        true,
        Role.COMMON, null, null);
    User domainUser = new User(userId, "Joel", "joel@gmail.com", "hashedPassword", true,
        Role.COMMON, null, null);

    when(userRepository.findById(userId)).thenReturn(Optional.of(userPersist));
    when(userMapper.toDomain(userPersist)).thenReturn(domainUser);
    when(categoryRepository.findByIdAndUserId(categoryId, userId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> {
      service.create(userId, debitRequest);
    }).isInstanceOf(CategoryNotFoundException.class).hasMessage("Category with id 1 not found");

    verify(debitMapper, times(0)).toPersist(any(Debit.class));
    verify(debitMapper, times(0)).toResponse(any(DebitPersist.class));
    verify(debitRepository, times(0)).save(any(DebitPersist.class));

  }

}