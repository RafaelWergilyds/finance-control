package com.rafaelw.financeControl.application.service;

import com.rafaelw.financeControl.application.ports.in.CategoryService;
import com.rafaelw.financeControl.application.ports.out.CategoryRepositoryPort;
import com.rafaelw.financeControl.domain.model.entities.Category;
import com.rafaelw.financeControl.domain.factories.CategoryFactory;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private final CategoryRepositoryPort categoryRepositoryPort;

  @Autowired
  private CategoryFactory categoryFactory;

  public CategoryServiceImpl(CategoryRepositoryPort categoryRepositoryPort){
    this.categoryRepositoryPort = categoryRepositoryPort;
  }

  @Transactional(readOnly = true)
  public Optional<Category> findById(Long userId, Long categoryId) {
    return categoryRepositoryPort.findByIdAndUserId(categoryId, userId);
  }

  @Transactional(readOnly = true)
  public List<Category> findAll(Long userId) {
    return categoryRepositoryPort.findAllByUserId(userId);
  }

  @Transactional
  public Category create(Long userId, String name) {
    Category category = categoryFactory.create(userId, name);
    return categoryRepositoryPort.save(category);
  }

  @Transactional
  public Optional<Category> update(Long userId, Long id, String name) {
    return categoryRepositoryPort.findByIdAndUserId(id, userId).map(category -> {
      if(name != null){
        category.changeName(name);
      }

      return categoryRepositoryPort.save(category);
    });
  }

  @Transactional
  public void delete(Long userId, Long id) {
    categoryRepositoryPort.findByIdAndUserId(id, userId).ifPresent(category -> {
      categoryRepositoryPort.delete(id);
    });
  }
}
