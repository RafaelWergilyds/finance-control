package com.rafaelw.financeControl.infra.outbound.persistence.repository;

import com.rafaelw.financeControl.infra.outbound.persistence.repository.mappers.UserMapper;
import com.rafaelw.financeControl.application.ports.out.UserRepositoryPort;
import com.rafaelw.financeControl.domain.model.entities.User;
import com.rafaelw.financeControl.infra.outbound.persistence.entities.UserPersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User save(User user) {
        UserPersist userPersist = userMapper.toPersist(user);
        return userMapper.toDomain(jpaUserRepository.save(userPersist));
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll().stream().map(userMapper::toDomain).toList();
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id).map(userMapper::toDomain);
    }

    @Override
    public void delete(Long id) {
        jpaUserRepository.deleteById(id);
    }
}
