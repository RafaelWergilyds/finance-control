package com.rafaelw.financeControl.infra.services;

import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.domain.service.VerifyUserByEmail;
import com.rafaelw.financeControl.infra.db.entities.UserEntity;
import com.rafaelw.financeControl.infra.db.repository.JpaUserRepository;
import com.rafaelw.financeControl.infra.dto.user.UserRequestDTO;
import com.rafaelw.financeControl.infra.dto.user.UserResponseDTO;
import com.rafaelw.financeControl.infra.dto.user.UserUpdateDTO;
import com.rafaelw.financeControl.infra.mappers.UserMapper;
import com.rafaelw.financeControl.infra.services.exceptions.UserNotFoundException;
import com.rafaelw.financeControl.infra.services.factories.UserFactory;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  @Autowired
  private VerifyUserByEmail verifyUserByEmail;

  @Autowired
  private JpaUserRepository repository;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private UserFactory userFactory;

  @Transactional(readOnly = true)
  public List<UserResponseDTO> findAll() {
    List<UserEntity> userEntities = repository.findAll();
    List<User> domainUsers = userEntities.stream().map(userEntity ->
        userMapper.toUser(userEntity)).toList();

    return domainUsers.stream().map(domainUser ->
        userMapper.toResponseDTO(domainUser)).toList();
  }

  @Transactional(readOnly = true)
  public UserResponseDTO findById(Long id) {
    UserEntity userEntity = repository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(id));

    return userMapper.toResponseDTO(userEntity);
  }

  @Transactional
  public UserResponseDTO insert(UserRequestDTO data) {
    User user = userFactory.create(data.name(), data.email(), data.password());
    UserEntity userEntity = userMapper.toUserEntity(user);

    repository.save(userEntity);

    return userMapper.toResponseDTO(userEntity);
  }

  @Transactional
  public UserResponseDTO update(Long userId, UserUpdateDTO data) {
    UserEntity userEntity = repository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    User user = userMapper.toUser(userEntity);
    if (data.name() != null) {
      user.changeName(data.name());
    }
    if (data.email() != null) {
      user.changeEmail(data.email(), verifyUserByEmail);
    }
    if (data.password() != null) {
      user.changePassword(data.password());
    }

    UserEntity updatedUser = userMapper.toUserEntity(user);
    repository.save(updatedUser);

    return userMapper.toResponseDTO(updatedUser);

  }

  @Transactional
  public void delete(Long id) {
    Optional<UserEntity> userEntity = repository.findById(id);
    if (userEntity.isEmpty()) {
      throw new UserNotFoundException(id);
    }
    repository.deleteById(userEntity.get().getId());
  }

}
