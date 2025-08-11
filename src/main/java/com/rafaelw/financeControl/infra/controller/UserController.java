package com.rafaelw.financeControl.infra.controller;

import com.rafaelw.financeControl.infra.dto.user.UserRequestDTO;
import com.rafaelw.financeControl.infra.dto.user.UserResponseDTO;
import com.rafaelw.financeControl.infra.dto.user.UserUpdateDTO;
import com.rafaelw.financeControl.infra.services.UserService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService service;


  @GetMapping
  public ResponseEntity<List<UserResponseDTO>> findAll() {
    List<UserResponseDTO> list = service.findAll();
    return ResponseEntity.ok().body(list);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
    UserResponseDTO response = service.findById(id);
    return ResponseEntity.ok().body(response);
  }

  @PostMapping
  public ResponseEntity<UserResponseDTO> insert(@Valid @RequestBody UserRequestDTO data) {
    UserResponseDTO response = service.insert(data);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(response).toUri();
    return ResponseEntity.created(uri).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponseDTO> update(@PathVariable Long id,
      @Valid @RequestBody UserUpdateDTO data) {
    UserResponseDTO response = service.update(id, data);
    return ResponseEntity.ok().body(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
  
}
