package com.rafaelw.financeControl.infra.controller;

import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.infra.db.entities.CategoryEntity;
import com.rafaelw.financeControl.infra.dto.category.CategoryRequestDTO;
import com.rafaelw.financeControl.infra.services.CategoryService;
import java.net.URI;
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
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id){
      Category category = categoryService.findById(id);
      return ResponseEntity.ok().body(category);
    }

    @PostMapping
    public ResponseEntity<CategoryEntity> createCategory(@RequestBody CategoryRequestDTO data){
      CategoryEntity categoryEntity = categoryService.createCategory(data);
      URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoryEntity.getId()).toUri();
      return ResponseEntity.created(uri).body(categoryEntity);
    }
}
