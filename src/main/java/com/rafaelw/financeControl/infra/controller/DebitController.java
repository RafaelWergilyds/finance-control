package com.rafaelw.financeControl.infra.controller;

import com.rafaelw.financeControl.domain.entities.Debit;
import com.rafaelw.financeControl.infra.db.entities.DebitEntity;
import com.rafaelw.financeControl.infra.dto.debit.CreateDebitDTO;
import com.rafaelw.financeControl.infra.services.CreateDebitService;
import com.rafaelw.financeControl.infra.services.DebitService;
import jakarta.websocket.server.PathParam;
import java.net.URI;
import javax.swing.text.html.parser.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/debits")
public class DebitController {

  @Autowired
  private CreateDebitService createDebitService;

  @Autowired
  private DebitService debitService;

  @GetMapping(value = "/{id}")
  public ResponseEntity<Debit> findById(@PathVariable Long id){
    Debit debit = debitService.findById(id);
    return ResponseEntity.ok().body(debit);
  }

  @PostMapping
  public ResponseEntity<DebitEntity> createDebit(@RequestBody CreateDebitDTO data){
    DebitEntity debit = createDebitService.execute(data);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(debit.getId()).toUri();
    return ResponseEntity.created(uri).body(debit);

  }
}
