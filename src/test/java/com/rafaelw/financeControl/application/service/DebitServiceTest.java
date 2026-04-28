package com.rafaelw.financeControl.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rafaelw.financeControl.application.dto.DebitFilterDTO;
import com.rafaelw.financeControl.application.ports.out.CategoryRepositoryPort;
import com.rafaelw.financeControl.application.ports.out.DebitRepositoryPort;
import com.rafaelw.financeControl.application.service.exceptions.CategoryNotFoundException;
import com.rafaelw.financeControl.application.service.exceptions.DebitNotFoundException;
import com.rafaelw.financeControl.domain.model.entities.Category;
import com.rafaelw.financeControl.domain.model.entities.Debit;
import com.rafaelw.financeControl.infra.outbound.persistence.repository.utils.PaginatedResponse;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class DebitServiceTest {

  @Mock
  private DebitRepositoryPort debitRepositoryPort;

  @Mock
  private CategoryRepositoryPort categoryRepositoryPort;

  @InjectMocks
  private DebitServiceImpl service;


  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should be able to create a debit")
  void createDebit() {
    Long userId = 1L;
    String name = "Pizza";
    BigDecimal amount = BigDecimal.valueOf(50.00);

    Debit domainDebit = Debit.builder()
            .id(1L)
            .userId(userId)
            .name(name)
            .amount(amount)
            .moment(Instant.now())
            .build();

    when(debitRepositoryPort.save(any(Debit.class))).thenReturn(domainDebit);

    Debit response = service.create(userId, name, amount, null);

    assertThat(response.getId()).isEqualTo(1L);
    assertThat(response.getName()).isEqualTo(name);
    assertThat(response.getAmount()).isEqualTo(amount);

    verify(debitRepositoryPort, times(1)).save(any(Debit.class));
  }

  @Test
  @DisplayName("Should be able to create a debit with a category")
  void createDebitWithCategory() {
    Long userId = 1L;
    Long categoryId = 1L;
    String name = "Pizza";
    BigDecimal amount = BigDecimal.valueOf(50.00);

    Category domainCategory = Category.builder()
            .id(categoryId)
            .name("Food")
            .userId(userId)
            .build();

    Debit domainDebit = Debit.builder()
            .id(1L)
            .userId(userId)
            .name(name)
            .amount(amount)
            .categoryId(categoryId)
            .moment(Instant.now())
            .build();

    when(categoryRepositoryPort.findByIdAndUserId(categoryId, userId)).thenReturn(Optional.of(domainCategory));
    when(debitRepositoryPort.save(any(Debit.class))).thenReturn(domainDebit);

    Debit response = service.create(userId, name, amount, categoryId);

    assertThat(response.getId()).isEqualTo(1L);
    assertThat(response.getCategoryId()).isEqualTo(categoryId);

    verify(categoryRepositoryPort, times(1)).findByIdAndUserId(categoryId, userId);
    verify(debitRepositoryPort, times(1)).save(any(Debit.class));
  }

  @Test
  @DisplayName("Should not be able to create a debit with a unexist category")
  void createDebitWithUnexistCategory() {
    Long userId = 1L;
    Long categoryId = 1L;

    when(categoryRepositoryPort.findByIdAndUserId(categoryId, userId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> {
      service.create(userId, "Pizza", BigDecimal.valueOf(50.00), categoryId);
    }).isInstanceOf(CategoryNotFoundException.class).hasMessage("Category with id 1 not found");

    verify(debitRepositoryPort, times(0)).save(any(Debit.class));
  }

  @Test
  @DisplayName("Should be able to find a debit with id")
  void findDebitById() {
    Long userId = 1L;
    Long debitId = 1L;

    Debit domainDebit = Debit.builder()
            .id(debitId)
            .userId(userId)
            .name("Pizza")
            .amount(BigDecimal.valueOf(50.00))
            .moment(Instant.now())
            .build();

    when(debitRepositoryPort.findByIdAndUserId(debitId, userId)).thenReturn(Optional.of(domainDebit));

    Optional<Debit> response = service.findById(userId, debitId);

    assertThat(response).isPresent();
    assertThat(response.get().getId()).isEqualTo(debitId);

    verify(debitRepositoryPort, times(1)).findByIdAndUserId(debitId, userId);
  }

  @Test
  @DisplayName("Should return empty when debit not found")
  void findUnexistDebitById() {
    Long userId = 1L;
    Long debitId = 1L;

    when(debitRepositoryPort.findByIdAndUserId(debitId, userId)).thenReturn(Optional.empty());

    Optional<Debit> response = service.findById(userId, debitId);

    assertThat(response).isEmpty();
  }

  @Test
  @DisplayName("Should be able to find a list of debits paginated")
  void findPaginatedDebits() {
    Long userId = 1L;
    int pageSize = 10;
    DebitFilterDTO filter = new DebitFilterDTO(null, null, null, null, null, null);

    List<Debit> debits = IntStream.range(0, 10)
        .mapToObj(i -> Debit.builder()
            .id((long) i + 1)
            .name("Pizza")
            .amount(BigDecimal.valueOf(50))
            .userId(userId)
            .moment(Instant.now())
            .build()
        ).toList();

    PaginatedResponse<Debit> paginatedResponse = new PaginatedResponse<>(debits, 1L, 10L, 11L, null);

    when(debitRepositoryPort.findAllByUser(userId, filter, pageSize, null)).thenReturn(paginatedResponse);

    PaginatedResponse<Debit> response = service.findAll(userId, filter, pageSize, null);

    assertThat(response.data()).hasSize(10);
    assertThat(response.startCursor()).isEqualTo(1L);
    assertThat(response.endCursor()).isEqualTo(10L);

    verify(debitRepositoryPort, times(1)).findAllByUser(userId, filter, pageSize, null);
  }

  @Test
  @DisplayName("Should be able to update a debit")
  void updateDebit() {
    Long userId = 1L;
    Long debitId = 1L;
    String newName = "Parmigiana";

    Debit domainDebit = Debit.builder()
            .id(debitId)
            .userId(userId)
            .name("Pizza")
            .amount(BigDecimal.valueOf(50.00))
            .moment(Instant.now())
            .build();

    when(debitRepositoryPort.findByIdAndUserId(debitId, userId)).thenReturn(Optional.of(domainDebit));
    when(debitRepositoryPort.save(any(Debit.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Optional<Debit> response = service.update(userId, debitId, newName, null, null);

    assertThat(response).isPresent();
    assertThat(response.get().getName()).isEqualTo(newName);

    verify(debitRepositoryPort, times(1)).findByIdAndUserId(debitId, userId);
    verify(debitRepositoryPort, times(1)).save(any(Debit.class));
  }

  @Test
  @DisplayName("Should be able to delete a debit")
  void deleteDebit() {
    Long userId = 1L;
    Long debitId = 1L;

    Debit domainDebit = Debit.builder()
            .id(debitId)
            .userId(userId)
            .build();

    when(debitRepositoryPort.findByIdAndUserId(debitId, userId)).thenReturn(Optional.of(domainDebit));

    service.delete(userId, debitId);

    verify(debitRepositoryPort, times(1)).findByIdAndUserId(debitId, userId);
    verify(debitRepositoryPort, times(1)).delete(debitId);
  }

  @Test
  @DisplayName("Should be able to get total sum of debits")
  void getTotalSum() {
    Long userId = 1L;
    DebitFilterDTO filter = new DebitFilterDTO(null, null, null, null, null, null);
    BigDecimal total = BigDecimal.valueOf(100.00);

    when(debitRepositoryPort.getTotalSumAmount(userId, filter)).thenReturn(total);

    BigDecimal result = service.getTotalSum(userId, filter);

    assertThat(result).isEqualTo(total);

    verify(debitRepositoryPort, times(1)).getTotalSumAmount(userId, filter);
  }
}
