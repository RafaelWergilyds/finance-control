package com.rafaelw.financeControl.infra.controller;

import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.infra.db.entities.UserEntity;
import com.rafaelw.financeControl.infra.dto.user.UserDTO;
import com.rafaelw.financeControl.infra.services.UserService;
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
    public ResponseEntity<List<User>> findAll(){
      List<User> list = service.findAll();
      return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
      User user = service.findById(id);
      return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<UserEntity> insert(@RequestBody UserDTO data){
      UserEntity user = service.insert(data);
      URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
      return ResponseEntity.created(uri).body(user);
    }

}
