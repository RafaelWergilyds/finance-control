package com.rafaelw.financeControl.application.services;

import com.rafaelw.financeControl.application.dto.user.UserRequestDTO;
import com.rafaelw.financeControl.application.dto.user.UserResponseDTO;
import com.rafaelw.financeControl.application.dto.user.UserUpdateDTO;
import com.rafaelw.financeControl.application.mappers.UserMapper;
import com.rafaelw.financeControl.application.services.exceptions.UserNotFoundException;
import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.domain.factories.UserFactory;
import com.rafaelw.financeControl.domain.service.VerifyUserByEmail;
import com.rafaelw.financeControl.infra.persist.entities.UserPersist;
import com.rafaelw.financeControl.infra.persist.repository.JpaUserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  @Autowired
  private VerifyUserByEmail verifyUserByEmail;

  @Autowired
  private JpaUserRepository userRepository;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private UserFactory userFactory;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Transactional(readOnly = true)
  public List<UserResponseDTO> findAll() {
    List<UserPersist> userPersists = userRepository.findAll();

    return userPersists.stream().map(userPersist ->
        userMapper.toResponseDTO(userPersist)).toList();
  }

  @Transactional(readOnly = true)
  public UserResponseDTO findById(Long id) {
    UserPersist userPersist = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(id));

    return userMapper.toResponseDTO(userPersist);
  }

  @Transactional
  public UserResponseDTO insert(UserRequestDTO data) {
    verifyUserByEmail.execute(data.email());
    User user = userFactory.create(data.name(), data.email(),
        passwordEncoder.encode(data.password()));

    UserPersist userPersist = userMapper.toPersist(user);
    UserPersist savedUser = userRepository.save(userPersist);

    return userMapper.toResponseDTO(savedUser);
  }

  @Transactional
  public UserResponseDTO update(Long userId, UserUpdateDTO data) {
    UserPersist userPersist = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    User user = userMapper.toDomain(userPersist);
    if (data.name() != null) {
      user.changeName(data.name());
    }
    if (data.email() != null) {
      verifyUserByEmail.execute(data.email());
      user.changeEmail(data.email());
    }
    if (data.password() != null) {
      user.changePassword(passwordEncoder.encode(data.password()));
    }

    UserPersist updatedUser = userMapper.toPersist(user);
    userRepository.save(updatedUser);

    return userMapper.toResponseDTO(updatedUser);

  }

  @Transactional
  public void delete(Long id) {
    Optional<UserPersist> userPersist = userRepository.findById(id);
    if (userPersist.isEmpty()) {
      throw new UserNotFoundException(id);
    }
    userRepository.deleteById(userPersist.get().getId());
  }

}
