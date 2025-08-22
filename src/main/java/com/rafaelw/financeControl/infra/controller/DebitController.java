package com.rafaelw.financeControl.infra.controller;

import com.rafaelw.financeControl.application.dto.debit.DebitFilterDTO;
import com.rafaelw.financeControl.application.dto.debit.DebitRequestDTO;
import com.rafaelw.financeControl.application.dto.debit.DebitResponseDTO;
import com.rafaelw.financeControl.application.dto.debit.DebitUpdateDTO;
import com.rafaelw.financeControl.application.dto.debit.PaginatedDebitsResponse;
import com.rafaelw.financeControl.application.dto.debit.PaginationDTO;
import com.rafaelw.financeControl.application.dto.debit.TotalDebitsResponse;
import com.rafaelw.financeControl.application.services.DebitService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/users/{userId}/debits")
public class DebitController {

  @Autowired
  private DebitService debitService;

  @GetMapping(value = "/{debitId}")
  public ResponseEntity<DebitResponseDTO> findById(@PathVariable Long userId,
      @PathVariable Long debitId) {
    DebitResponseDTO response = debitService.findById(userId, debitId);
    return ResponseEntity.ok().body(response);
  }


  @GetMapping
  public ResponseEntity<List<DebitResponseDTO>> findAllFiltered(@PathVariable Long userId,
      DebitFilterDTO filter, PaginationDTO page) {
    List<DebitResponseDTO> response = debitService.findAll(userId, filter, page);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/paginated")
  public ResponseEntity<List<DebitResponseDTO>> findAllPaginate(@PathVariable Long userId,
      DebitFilterDTO filter, Integer pageSize, Long cursor) {
    PaginatedDebitsResponse response = debitService.findAllPage(userId, filter, pageSize, cursor);
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add("X-Start-Cursor", String.valueOf(response.startCursor()));
    responseHeaders.add("X-End-Cursor", String.valueOf(response.endCursor()));
    responseHeaders.add("X-Next-Page", String.valueOf(response.nextPage()));
    responseHeaders.add("X-Previous-Page", String.valueOf(response.previousPage()));
    return ResponseEntity.ok().headers(responseHeaders).body(response.data());
  }

  @PostMapping
  public ResponseEntity<DebitResponseDTO> createDebit(@PathVariable Long userId,
      @RequestBody DebitRequestDTO data) {
    DebitResponseDTO response = debitService.create(userId, data);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{debitId}")
        .buildAndExpand(userId, response.id()).toUri();
    return ResponseEntity.created(uri).body(response);

  }

  @PutMapping("/{debitId}")
  public ResponseEntity<DebitResponseDTO> update(@PathVariable Long userId,
      @PathVariable Long debitId, @RequestBody
      DebitUpdateDTO data) {
    DebitResponseDTO response = debitService.update(userId, debitId, data);
    return ResponseEntity.ok().body(response);
  }

  @DeleteMapping("/{debitId}")
  public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long debitId) {
    debitService.delete(userId, debitId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/totalDebits")
  public ResponseEntity<TotalDebitsResponse> getTotalDebits(@PathVariable Long userId,
      DebitFilterDTO filter) {
    TotalDebitsResponse response = debitService.getTotalSum(userId, filter);
    return ResponseEntity.ok().body(response);
  }

}
