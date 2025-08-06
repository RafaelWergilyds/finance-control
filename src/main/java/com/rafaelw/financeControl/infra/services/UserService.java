package com.rafaelw.financeControl.infra.services;

import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.domain.entities.enums.Role;
import com.rafaelw.financeControl.infra.db.entities.UserEntity;
import com.rafaelw.financeControl.infra.db.repository.UserRepository;
import com.rafaelw.financeControl.infra.dto.user.UserRequestDTO;
import com.rafaelw.financeControl.infra.dto.user.UserResponseDTO;
import com.rafaelw.financeControl.infra.mappers.UserMapper;
import com.rafaelw.financeControl.infra.services.exceptions.UserAlreadyExistsException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper userMapper;

    public List<UserResponseDTO> findAll(){
      List<UserEntity> userEntities = repository.findAll();
      List<User> domainUsers = userEntities.stream().map(userEntity ->
        userMapper.toUser(userEntity)).toList();

      List<UserResponseDTO> usersResponseDTOS = domainUsers.stream().map(domainUser ->
          userMapper.toResponseDTO(domainUser)).toList();

      return usersResponseDTOS;
    }

    public UserResponseDTO findById(Long id){
      UserEntity userEntity = repository.findById(id).orElse(new UserEntity());

      UserResponseDTO user = userMapper.toResponseDTO(userEntity);

      return user;
    }

    public UserEntity insert(UserRequestDTO data){
      Optional<UserEntity> userAlreadyExist = repository.findByEmail(data.email());

      if (userAlreadyExist.isPresent()){
        throw new UserAlreadyExistsException(data.email());
      }

      User user = new User(null, data.name(), data.email(), data.password(), Role.COMMON, null, null);
      UserEntity userEntity = userMapper.toUserEntity(user);
      return repository.save(userEntity);
    }


}
