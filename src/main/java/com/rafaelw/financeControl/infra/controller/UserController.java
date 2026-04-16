package com.rafaelw.financeControl.infra.controller;

import com.rafaelw.financeControl.application.dto.user.UserRequestDTO;
import com.rafaelw.financeControl.application.dto.user.UserResponseDTO;
import com.rafaelw.financeControl.application.dto.user.UserUpdateDTO;
import com.rafaelw.financeControl.application.services.UserService;
import com.rafaelw.financeControl.application.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/users")
@Tag(name = "Users", description = "Operations for users")
public class UserController {

  @Autowired
  private UserService service;


  @Operation(summary = "Find Users", description = "Search for all users in the system")
  @GetMapping
  public ResponseEntity<List<UserResponseDTO>> findAll() {
    List<UserResponseDTO> list = service.findAll();
    return ResponseEntity.ok().body(list);
  }

  @Operation(summary = "Find by Id", description = "Find a user by id")
  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
    UserResponseDTO response = service.findById(id);
    return ResponseEntity.ok().body(response);
  }

  @Operation(summary = "Create User", description = "Create a user with name, email and password")
  @PostMapping
  public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO data) {
    UserResponseDTO response = service.create(data);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(response).toUri();
    return ResponseEntity.created(uri).body(response);
  }

  @Operation(summary = "Update User", description = "Update the fields of an existing user by id ")
  @PutMapping("/{id}")
  public ResponseEntity<UserResponseDTO> update(@PathVariable Long id,
      @Valid @RequestBody UserUpdateDTO data) {
    UserResponseDTO response = service.update(id, data);
    return ResponseEntity.ok().body(response);
  }

  @Operation(summary = "Delete User", description = "Delete a existing user by id")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Get Profile", description = "Get the user's profile using their access token ")
  @GetMapping("/profile")
  public ResponseEntity<UserResponseDTO> getProfile(Authentication authentication) {
    Long userId = SecurityUtils.getUserId(authentication);
    UserResponseDTO response = service.findById(userId);
    return ResponseEntity.ok().body(response);
  }

  @Operation(summary = "Update Profile", description = "Update the fields of user's profile")
  @PutMapping("/profile")
  public ResponseEntity<UserResponseDTO> updateProfile(Authentication authentication,
      @Valid @RequestBody UserUpdateDTO data) {
    Long userId = SecurityUtils.getUserId(authentication);
    UserResponseDTO response = service.update(userId, data);
    return ResponseEntity.ok().body(response);
  }

}
