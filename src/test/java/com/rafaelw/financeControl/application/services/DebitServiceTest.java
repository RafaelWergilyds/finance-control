package com.rafaelw.financeControl.application.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rafaelw.financeControl.application.dto.debit.DebitFilterDTO;
import com.rafaelw.financeControl.application.dto.debit.DebitRequestDTO;
import com.rafaelw.financeControl.application.dto.debit.DebitResponseDTO;
import com.rafaelw.financeControl.application.mappers.CategoryMapper;
import com.rafaelw.financeControl.application.mappers.DebitMapper;
import com.rafaelw.financeControl.application.mappers.UserMapper;
import com.rafaelw.financeControl.application.services.exceptions.CategoryNotFoundException;
import com.rafaelw.financeControl.application.services.exceptions.DebitNotFoundException;
import com.rafaelw.financeControl.application.utils.PaginatedResponse;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

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
    BigDecimal amount = BigDecimal.valueOf(50.00);
    Instant moment = Instant.parse("2025-10-10T10:00:00Z");

    DebitRequestDTO debitRequest = new DebitRequestDTO("Pizza", amount,
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

    Debit createdDebit = new Debit(domainUser, "Pizza", amount, domainCategory);
    DebitPersist debitPersist = new DebitPersist(debitId, "Pizza", amount,
        moment, userPersist, categoryPersist);
    DebitResponseDTO debitResponse = new DebitResponseDTO(debitId, "Pizza",
        amount,
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
    assertThat(response.amount()).isEqualTo(amount);
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

  @Test
  @DisplayName("Should be able to find a debit with id")
  void findDebitById() {
    Long userId = 1L;
    Long debitId = 1L;
    String name = "Pizza";
    BigDecimal amount = BigDecimal.valueOf(50.00);
    Instant moment = Instant.parse("2025-10-10T10:00:00Z");

    UserPersist userPersist = new UserPersist(userId, "Joel", "joel@gmail.com", "hashedPassword",
        true,
        Role.COMMON, null, null);

    DebitPersist foundedDebit = new DebitPersist(debitId, name, amount,
        moment, userPersist, null);
    DebitResponseDTO debitResponse = new DebitResponseDTO(debitId, name,
        amount, moment, null);

    when(debitRepository.findByIdAndUserId(debitId, userId)).thenReturn(Optional.of(foundedDebit));
    when(debitMapper.toResponse(foundedDebit)).thenReturn(debitResponse);

    DebitResponseDTO response = service.findById(userId, debitId);

    assertThat(response.id()).isEqualTo(debitId);
    assertThat(response.name()).isEqualTo(name);
    assertThat(response.amount()).isEqualTo(amount);
    assertThat(response.moment()).isEqualTo(moment);
    assertThat(response.categoryId()).isNull();

    verify(debitRepository, times(1)).findByIdAndUserId(debitId, userId);
    verify(debitMapper, times(1)).toResponse(any(DebitPersist.class));

  }

  @Test
  @DisplayName("Should not be able to find a unexist debit")
  void findUnexistDebitById() {
    Long userId = 1L;
    Long debitId = 1L;

    when(debitRepository.findByIdAndUserId(debitId, userId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> {
      service.findById(userId, debitId);
    }).isInstanceOf(
        DebitNotFoundException.class).hasMessage("Debit with id 1 not found");

    verify(debitMapper, times(0)).toResponse(any(DebitPersist.class));

  }

  @Test
  @DisplayName("Should be able to find a list of debits paginated")
  void findPaginatedDebits() {
    Long userId = 1L;
    int pagesize = 10;

    UserPersist user = new UserPersist(userId, "Joel", "joel@gmail.com",
        "hashedPassword", true, Role.ADMIN,
        null, null);

    CategoryPersist category = new CategoryPersist(1L, "Food", user, null);
    user.setCategories(Set.of(category));
    DebitFilterDTO filter = new DebitFilterDTO(null, null, null, null, null, null);

    List<DebitPersist> debits = IntStream.range(0, 11)
        .mapToObj(i -> new DebitPersist(
            (long) i + 1,
            "Pizza",
            BigDecimal.valueOf(50),
            Instant.now(),
            user,
            category
        )).toList();

    Pageable pageable = PageRequest.of(0, pagesize, Sort.by("id"));
    Page<DebitPersist> page = new PageImpl<>(debits, pageable, 10);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(debitRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
    when(debitMapper.toResponse(any(DebitPersist.class))).thenAnswer(invocation -> {
      DebitPersist debit = invocation.getArgument(0);
      return new DebitResponseDTO(debit.getId(), debit.getName(), debit.getAmount(),
          debit.getMoment(), debit.getCategory().getId());
    });

    PaginatedResponse<DebitResponseDTO> response = service.findAll(userId, filter, pagesize,
        null);

    assertThat(response.data().size()).isEqualTo(10);
    assertThat(response.data().getFirst().id()).isEqualTo(1L);
    assertThat(response.data().getLast().id()).isEqualTo(10L);
    assertThat(response.startCursor()).isEqualTo(1L);
    assertThat(response.endCursor()).isEqualTo(10L);
    assertThat(response.previousPage()).isNull();
    assertThat(response.nextPage()).isEqualTo(11L);

    verify(userRepository, times(1)).findById(userId);
    verify(debitRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
  }

  @Test
  @DisplayName("Should be able to find a list of debits paginated with filter")
  void findPaginatedDebitsWithFilter() {
    Long userId = 1L;
    int pagesize = 10;
    Long foodCategoryId = 1L;
    Long electronicCategoryId = 2L;

    UserPersist user = new UserPersist(userId, "Joel", "joel@gmail.com",
        "hashedPassword", true, Role.ADMIN,
        null, null);

    CategoryPersist foodCategory = new CategoryPersist(foodCategoryId, "Food", user, null);
    CategoryPersist electronicCategory = new CategoryPersist(electronicCategoryId, "Games", user,
        null);
    user.setCategories(Set.of(foodCategory, electronicCategory));

    List<DebitPersist> allDebits = List.of(
        new DebitPersist(1L, "Pizza", BigDecimal.valueOf(50), Instant.now(), user, foodCategory),
        new DebitPersist(2L, "Mouse", BigDecimal.valueOf(30), Instant.now(), user,
            electronicCategory),
        new DebitPersist(3L, "Parmigiana", BigDecimal.valueOf(20), Instant.now(), user,
            foodCategory)
    );

    DebitFilterDTO filter = new DebitFilterDTO(foodCategoryId, null, null,
        null, null, null);

    List<DebitPersist> expectedDebits = allDebits.stream()
        .filter(debit -> debit.getCategory().getId().equals(foodCategoryId))
        .toList();

    Pageable pageable = PageRequest.of(0, pagesize, Sort.by("id"));
    Page<DebitPersist> page = new PageImpl<>(expectedDebits, pageable, expectedDebits.size());

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(debitRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
    when(debitMapper.toResponse(any(DebitPersist.class))).thenAnswer(invocation -> {
      DebitPersist debit = invocation.getArgument(0);
      return new DebitResponseDTO(debit.getId(), debit.getName(), debit.getAmount(),
          debit.getMoment(), debit.getCategory().getId());
    });

    PaginatedResponse<DebitResponseDTO> response = service.findAll(userId, filter, pagesize,
        null);

    assertThat(response.data().size()).isEqualTo(2);
    assertThat(
        response.data().stream().map(DebitResponseDTO::categoryId)
            .toList()).containsAll(Collections.singleton(1L));
    assertThat(response.startCursor()).isEqualTo(1L);
    assertThat(response.endCursor()).isEqualTo(3L);
    assertThat(response.previousPage()).isNull();
    assertThat(response.nextPage()).isNull();

    verify(userRepository, times(1)).findById(userId);
    verify(debitRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
  }
}