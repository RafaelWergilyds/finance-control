package com.rafaelw.financeControl.infra.services;

import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.domain.service.CreateCategory;
import com.rafaelw.financeControl.infra.db.entities.CategoryEntity;
import com.rafaelw.financeControl.infra.db.entities.UserEntity;
import com.rafaelw.financeControl.infra.db.repository.CategoryRepository;
import com.rafaelw.financeControl.infra.db.repository.UserRepository;
import com.rafaelw.financeControl.infra.dto.category.CategoryRequestDTO;
import com.rafaelw.financeControl.infra.mappers.CategoryMapper;
import com.rafaelw.financeControl.infra.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CreateCategory createCategory;


    public Category findById(Long id){
      CategoryEntity categoryEntity = categoryRepository.findById(id).orElse(null);
      Category category = categoryMapper.toCategory(categoryEntity);
      return category;
    }

    public CategoryEntity createCategory(CategoryRequestDTO data){
      UserEntity userEntity = userRepository.findById(data.userId()).orElse(new UserEntity());
      User user = userMapper.toUser(userEntity);

      Category category = createCategory.createCategory(user, data.name());
      CategoryEntity categoryEntity = categoryMapper.toCategoryEntity(category);
      categoryRepository.save(categoryEntity);

      return categoryEntity;


    }

}
