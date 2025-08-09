package com.rafaelw.financeControl.infra.controller;

import com.rafaelw.financeControl.infra.dto.category.CategoryRequestDTO;
import com.rafaelw.financeControl.infra.dto.category.CategoryResponseDTO;
import com.rafaelw.financeControl.infra.dto.category.CategoryUpdateDTO;
import com.rafaelw.financeControl.infra.services.CategoryService;
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
@RequestMapping("/users/{userId}/categories")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @GetMapping("/{categoryId}")
  public ResponseEntity<CategoryResponseDTO> findById(@PathVariable Long userId,
      @PathVariable Long categoryId) {
    CategoryResponseDTO response = categoryService.findById(userId, categoryId);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping
  public ResponseEntity<List<CategoryResponseDTO>> findAllByUser(@PathVariable Long userId) {
    List<CategoryResponseDTO> response = categoryService.findAllByUser(userId);
    return ResponseEntity.ok().body(response);
  }

  @PostMapping
  public ResponseEntity<CategoryResponseDTO> createCategory(@PathVariable Long userId,
      @RequestBody CategoryRequestDTO data) {
    CategoryResponseDTO response = categoryService.create(userId, data);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(userId, response.id()).toUri();
    return ResponseEntity.created(uri).body(response);
  }

  @PutMapping("/{categoryId}")
  public ResponseEntity<CategoryResponseDTO> update(@PathVariable Long userId,
      @PathVariable Long categoryId, @RequestBody CategoryUpdateDTO data) {
    CategoryResponseDTO response = categoryService.update(userId, categoryId, data);
    return ResponseEntity.ok().body(response);
  }

  @DeleteMapping("/{categoryId}")
  public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long categoryId) {
    categoryService.delete(userId, categoryId);
    return ResponseEntity.noContent().build();
  }
}
