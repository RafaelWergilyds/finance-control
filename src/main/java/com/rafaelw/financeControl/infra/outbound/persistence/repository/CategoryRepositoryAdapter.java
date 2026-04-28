package com.rafaelw.financeControl.infra.outbound.persistence.repository;

import com.rafaelw.financeControl.infra.outbound.persistence.repository.mappers.CategoryMapper;
import com.rafaelw.financeControl.application.ports.out.CategoryRepositoryPort;
import com.rafaelw.financeControl.domain.model.entities.Category;
import com.rafaelw.financeControl.infra.outbound.persistence.entities.CategoryPersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryRepositoryAdapter implements CategoryRepositoryPort {

    @Autowired
    private JpaCategoryRepository jpaCategoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Category save(Category category) {
        CategoryPersist categoryPersist = categoryMapper.toPersist(category);
        return categoryMapper.toDomain(jpaCategoryRepository.save(categoryPersist));
    }

    @Override
    public List<Category> findAllByUserId(Long userId) {
        return jpaCategoryRepository.findAllByUserId(userId).stream().map(categoryMapper::toDomain).toList();
    }

    @Override
    public Optional<Category> findByIdAndUserId(Long id, Long userId) {
        return jpaCategoryRepository.findByIdAndUserId(id, userId).map(categoryMapper::toDomain);
    }

    @Override
    public void delete(Long id) {
        jpaCategoryRepository.deleteById(id);
    }
}
