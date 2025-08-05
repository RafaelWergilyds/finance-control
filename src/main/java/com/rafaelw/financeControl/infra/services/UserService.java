package com.rafaelw.financeControl.infra.services;

import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.domain.entities.enums.Role;
import com.rafaelw.financeControl.infra.db.entities.UserEntity;
import com.rafaelw.financeControl.infra.db.repository.UserRepository;
import com.rafaelw.financeControl.infra.dto.user.UserDTO;
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

    public List<User> findAll(){
      List<UserEntity> userEntities = repository.findAll();
      List<User> users = userEntities.stream().map(userEntity ->
        userMapper.toUser(userEntity)).toList();
      return users;
    }

    public User findById(Long id){
      UserEntity userEntity = repository.findById(id).orElse(new UserEntity());

      User user = userMapper.toUser(userEntity);

      return user;
    }

    public UserEntity insert(UserDTO data){
      Optional<UserEntity> userAlreadyExist = repository.findByEmail(data.email());

      if (userAlreadyExist.isPresent()){
        throw new UserAlreadyExistsException(data.email());
      }

      User user = new User(null, data.name(), data.email(), data.password(), Role.COMMON, null, null);
      UserEntity userEntity = userMapper.toUserEntity(user);
      return repository.save(userEntity);
    }


}
