package com.rafaelw.financeControl.infra.controller;

import com.rafaelw.financeControl.application.dto.category.CategoryRequestDTO;
import com.rafaelw.financeControl.application.dto.category.CategoryResponseDTO;
import com.rafaelw.financeControl.application.dto.category.CategoryUpdateDTO;
import com.rafaelw.financeControl.application.services.CategoryService;
import com.rafaelw.financeControl.application.utils.SecurityUtils;
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
@RequestMapping("/categories")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @GetMapping("/{categoryId}")
  public ResponseEntity<CategoryResponseDTO> findById(Authentication authentication,
      @PathVariable Long categoryId) {
    Long userId = SecurityUtils.getUserId(authentication);
    CategoryResponseDTO response = categoryService.findById(userId, categoryId);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping
  public ResponseEntity<List<CategoryResponseDTO>> findAllByUser(Authentication authentication) {
    Long userId = SecurityUtils.getUserId(authentication);
    List<CategoryResponseDTO> response = categoryService.findAllByUser(userId);
    return ResponseEntity.ok().body(response);
  }

  @PostMapping
  public ResponseEntity<CategoryResponseDTO> createCategory(Authentication authentication,
      @RequestBody CategoryRequestDTO data) {
    Long userId = SecurityUtils.getUserId(authentication);
    CategoryResponseDTO response = categoryService.create(userId, data);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(response.id()).toUri();
    return ResponseEntity.created(uri).body(response);
  }

  @PutMapping("/{categoryId}")
  public ResponseEntity<CategoryResponseDTO> update(Authentication authentication,
      @PathVariable Long categoryId, @RequestBody CategoryUpdateDTO data) {
    Long userId = SecurityUtils.getUserId(authentication);
    CategoryResponseDTO response = categoryService.update(userId, categoryId, data);
    return ResponseEntity.ok().body(response);
  }

  @DeleteMapping("/{categoryId}")
  public ResponseEntity<Void> delete(Authentication authentication, @PathVariable Long categoryId) {
    Long userId = SecurityUtils.getUserId(authentication);
    categoryService.delete(userId, categoryId);
    return ResponseEntity.noContent().build();
  }
}
