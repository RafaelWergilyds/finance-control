package com.rafaelw.financeControl.infra.controller;

import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.infra.db.entities.UserEntity;
import com.rafaelw.financeControl.infra.dto.user.UserRequestDTO;
import com.rafaelw.financeControl.infra.dto.user.UserResponseDTO;
import com.rafaelw.financeControl.infra.services.UserService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
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
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private UserService service;


    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll(){
      List<UserResponseDTO> list = service.findAll();
      return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){
      UserResponseDTO user = service.findById(id);
      return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> insert(@Valid @RequestBody UserRequestDTO data){
      UserResponseDTO user = service.insert(data);
      URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user).toUri();
      return ResponseEntity.created(uri).body(user);
    }

}
