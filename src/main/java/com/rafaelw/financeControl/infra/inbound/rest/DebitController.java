package com.rafaelw.financeControl.infra.inbound.rest;

import com.rafaelw.financeControl.infra.inbound.rest.dto.debit.DebitFilterDTO;
import com.rafaelw.financeControl.infra.inbound.rest.dto.debit.DebitRequestDTO;
import com.rafaelw.financeControl.infra.inbound.rest.dto.debit.DebitResponseDTO;
import com.rafaelw.financeControl.infra.inbound.rest.dto.debit.DebitUpdateDTO;
import com.rafaelw.financeControl.infra.inbound.rest.dto.debit.TotalDebitsResponse;
import com.rafaelw.financeControl.application.service.DebitService;
import com.rafaelw.financeControl.application.utils.PaginatedResponse;
import com.rafaelw.financeControl.application.utils.SecurityUtils;
import com.rafaelw.financeControl.infra.inbound.rest.headers.PaginationHeader;
import java.net.URI;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/debits")
@Tag(name = "Debits", description = "Operations for debits")
public class DebitController {

  @Autowired
  private DebitService debitService;

  @Autowired
  private PaginationHeader paginationHeader;

  @Operation(summary = "Find by Id", description = "find a debit by id")
  @GetMapping(value = "/{debitId}")
  public ResponseEntity<DebitResponseDTO> findById(Authentication authentication,
      @PathVariable Long debitId) {
    Long userId = SecurityUtils.getUserId(authentication);
    DebitResponseDTO response = debitService.findById(userId, debitId);
    return ResponseEntity.ok().body(response);
  }

  @Operation(summary = "Find All User's Debit", description = "Find the user's debit registered")
  @GetMapping
  public ResponseEntity<List<DebitResponseDTO>> findAllPaginate(
      Authentication authentication,
      DebitFilterDTO filter, Integer pageSize, Long cursor) {
    Long userId = SecurityUtils.getUserId(authentication);
    PaginatedResponse<DebitResponseDTO> response = debitService.findAll(userId, filter,
        pageSize, cursor);
    HttpHeaders responseHeaders = paginationHeader.execute(response);
    return ResponseEntity.ok().headers(responseHeaders).body(response.data());
  }

  @Operation(summary = "Create Debit", description = "Create a debit for the user")
  @PostMapping
  public ResponseEntity<DebitResponseDTO> createDebit(Authentication authentication,
      @RequestBody DebitRequestDTO data) {
    Long userId = SecurityUtils.getUserId(authentication);
    DebitResponseDTO response = debitService.create(userId, data);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{debitId}")
        .buildAndExpand(response.id()).toUri();
    return ResponseEntity.created(uri).body(response);
  }

  @Operation(summary = "Update Debit", description = "Updates a user's debit")
  @PutMapping("/{debitId}")
  public ResponseEntity<DebitResponseDTO> update(Authentication authentication,
      @PathVariable Long debitId, @RequestBody
      DebitUpdateDTO data) {
    Long userId = SecurityUtils.getUserId(authentication);
    DebitResponseDTO response = debitService.update(userId, debitId, data);
    return ResponseEntity.ok().body(response);
  }

  @Operation(summary = "Delete Debit", description = "Delete a user's debit")
  @DeleteMapping("/{debitId}")
  public ResponseEntity<Void> delete(Authentication authentication, @PathVariable Long debitId) {
    Long userId = SecurityUtils.getUserId(authentication);
    debitService.delete(userId, debitId);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Add up all debits", description = "add up the amounts of the all debits")
  @GetMapping("/totalDebits")
  public ResponseEntity<TotalDebitsResponse> getTotalDebits(Authentication authentication,
      DebitFilterDTO filter) {
    Long userId = SecurityUtils.getUserId(authentication);
    TotalDebitsResponse response = debitService.getTotalSum(userId, filter);
    return ResponseEntity.ok().body(response);
  }

}
