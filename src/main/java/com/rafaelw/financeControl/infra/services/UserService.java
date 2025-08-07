package com.rafaelw.financeControl.infra.services;

import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.domain.service.VerifyUserByEmail;
import com.rafaelw.financeControl.infra.db.entities.UserEntity;
import com.rafaelw.financeControl.infra.db.repository.JpaUserRepository;
import com.rafaelw.financeControl.infra.dto.user.UserRequestDTO;
import com.rafaelw.financeControl.infra.dto.user.UserResponseDTO;
import com.rafaelw.financeControl.infra.mappers.UserMapper;
import com.rafaelw.financeControl.infra.services.exceptions.UserNotFoundException;
import com.rafaelw.financeControl.infra.services.factories.UserFactory;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
  private JpaUserRepository repository;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private UserFactory userFactory;

    public List<UserResponseDTO> findAll(){
      List<UserEntity> userEntities = repository.findAll();
      List<User> domainUsers = userEntities.stream().map(userEntity ->
        userMapper.toUser(userEntity)).toList();

      return domainUsers.stream().map(domainUser ->
          userMapper.toResponseDTO(domainUser)).toList();
    }

    public UserResponseDTO findById(Long id){
      Optional<UserEntity> userEntity = repository.findById(id);
      return userEntity
          .map(entity -> userMapper.toResponseDTO(entity))
          .orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserResponseDTO insert(UserRequestDTO data){
      User user = userFactory.create(data.name(), data.email(), data.password(), data.role());
      UserEntity userEntity = userMapper.toUserEntity(user);
      repository.save(userEntity);
      return userMapper.toResponseDTO(userEntity);
    }


}
