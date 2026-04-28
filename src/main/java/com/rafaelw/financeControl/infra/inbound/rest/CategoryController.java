package com.rafaelw.financeControl.infra.inbound.rest;

import com.rafaelw.financeControl.domain.model.entities.Category;
import com.rafaelw.financeControl.infra.inbound.rest.dto.category.CategoryRequestDTO;
import com.rafaelw.financeControl.infra.inbound.rest.dto.category.CategoryResponseDTO;
import com.rafaelw.financeControl.infra.inbound.rest.dto.category.CategoryUpdateDTO;
import com.rafaelw.financeControl.application.service.CategoryServiceImpl;
import com.rafaelw.financeControl.infra.inbound.rest.utils.SecurityUtils;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/categories")
@Tag(name = "Categories", description = "Operations for categories")
public class CategoryController {

  @Autowired
  private CategoryServiceImpl categoryService;

  @Operation(summary = "Create Category", description = "Creates a new category for the authenticated user")
  @PostMapping
  public ResponseEntity<CategoryResponseDTO> create(Authentication authentication,
                                                    @RequestBody CategoryRequestDTO data) {
    Long userId = SecurityUtils.getUserId(authentication);
    Category category = categoryService.create(userId, data.name());

    return ResponseEntity.ok().body(CategoryResponseDTO.fromDomain(category));
  }

  @Operation(summary = "Find Category by ID", description = "Finds a category by its ID for the authenticated user")
  @GetMapping("/{categoryId}")
  public ResponseEntity<CategoryResponseDTO> findById(Authentication authentication,
      @PathVariable Long categoryId) {

    Long userId = SecurityUtils.getUserId(authentication);
    return categoryService.findById(userId, categoryId)
            .map(CategoryResponseDTO::fromDomain)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @Operation(summary = "Find All User's Categories", description = "Retrieves all categories associated with the authenticated user")
  @GetMapping
  public ResponseEntity<List<CategoryResponseDTO>> findAllByUser(Authentication authentication) {
    Long userId = SecurityUtils.getUserId(authentication);
    List<Category> list = categoryService.findAll(userId);

    return ResponseEntity.ok().body(list.stream().map(CategoryResponseDTO::fromDomain).toList());
  }


  @Operation(summary = "Update Category", description = "Updates an existing category for the authenticated user")
  @PutMapping("/{categoryId}")
  public ResponseEntity<CategoryResponseDTO> update(Authentication authentication,
      @PathVariable Long categoryId, @RequestBody CategoryUpdateDTO data) {
    Long userId = SecurityUtils.getUserId(authentication);

    return categoryService.update(userId, categoryId, data.name())
            .map(CategoryResponseDTO::fromDomain)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @Operation(summary = "Delete Category", description = "Deletes a category for the authenticated user")
  @DeleteMapping("/{categoryId}")
  public ResponseEntity<Void> delete(Authentication authentication, @PathVariable Long categoryId) {
    Long userId = SecurityUtils.getUserId(authentication);
    categoryService.delete(userId, categoryId);
    return ResponseEntity.noContent().build();
  }
}
