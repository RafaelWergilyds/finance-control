package com.rafaelw.financeControl.infra.inbound.rest;

import com.rafaelw.financeControl.infra.inbound.rest.dto.user.UserRequestDTO;
import com.rafaelw.financeControl.infra.inbound.rest.dto.user.UserResponseDTO;
import com.rafaelw.financeControl.infra.inbound.rest.dto.user.UserUpdateDTO;
import com.rafaelw.financeControl.application.usecase.UserUseCaseImpl;
import com.rafaelw.financeControl.application.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Operations for users")
public class UserController {

  @Autowired
  private UserUseCaseImpl userUseCase;


  @Operation(summary = "Find Users", description = "Search for all users in the system")
  @GetMapping
  public ResponseEntity<List<UserResponseDTO>> findAll() {
    var list = userUseCase.findAll();
    return ResponseEntity.ok().body(list.stream().map(UserResponseDTO::fromDomain).toList());
  }

  @Operation(summary = "Find by Id", description = "Find a user by id")
  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
    return userUseCase.findById(id)
            .map(UserResponseDTO::fromDomain)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @Operation(summary = "Create User", description = "Create a user with name, email and password")
  @PostMapping
  public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO data) {
    var user = userUseCase.create(data.name(), data.email(), data.password());
    return ResponseEntity.ok(UserResponseDTO.fromDomain(user));
  }

  @Operation(summary = "Update User", description = "Update the fields of an existing user by id ")
  @PutMapping("/{id}")
  public ResponseEntity<UserResponseDTO> update(@PathVariable Long id,
      @Valid @RequestBody UserUpdateDTO data) {
    return userUseCase.update(id, data.name(), data.email(), data.password())
            .map(UserResponseDTO::fromDomain)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @Operation(summary = "Delete User", description = "Delete a existing user by id")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    userUseCase.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Get Profile", description = "Get the user's profile using their access token ")
  @GetMapping("/profile")
  public ResponseEntity<UserResponseDTO> getProfile(Authentication authentication) {
    Long userId = SecurityUtils.getUserId(authentication);
    return userUseCase.findById(userId)
            .map(UserResponseDTO::fromDomain)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @Operation(summary = "Update Profile", description = "Update the fields of user's profile")
  @PutMapping("/profile")
  public ResponseEntity<UserResponseDTO> updateProfile(Authentication authentication,
      @Valid @RequestBody UserUpdateDTO data) {
    Long userId = SecurityUtils.getUserId(authentication);
    return userUseCase.update(userId, data.name(), data.email(), data.password())
            .map(UserResponseDTO::fromDomain)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

}
