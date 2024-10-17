package com.ntd.userservice.repository.impl;

import com.ntd.userservice.repository.BalanceRepository;
import com.ntd.userservice.repository.UserRepository;
import com.ntd.userservice.repository.dto.BalanceOutputDTO;
import com.ntd.userservice.repository.dto.UserBalanceOutputDTO;
import com.ntd.userservice.repository.dto.UserOutputDTO;
import com.ntd.userservice.repository.interfaces.UserJpaRepository;
import com.ntd.userservice.repository.model.UserEntity;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final BalanceRepository balanceRepository;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository, BalanceRepository balanceRepository) {
        this.userJpaRepository = userJpaRepository;
        this.balanceRepository = balanceRepository;
    }

    @Override
    public Optional<UserOutputDTO> findUserByUsername(String username) {
        return userJpaRepository.findUserEntityByUsername(username).stream().map(it -> new UserOutputDTO(it.getId(), it.getUsername(), it.getPass(), it.getRole())).findFirst();
    }

    @Override
    @Transactional
    public void save(String username, String password) {

        var user = userJpaRepository.save(new UserEntity(null, username, password, "DEFAULT"));

        balanceRepository.save(new BalanceOutputDTO(null, new UserOutputDTO(user.getId(), null, null, null), new BigDecimal("100"), null), BigDecimal.ZERO);

    }

    @Override
    public Optional<UserBalanceOutputDTO> findUserBalanceInfo(String username) {
        return userJpaRepository.findUserBalanceInfo(username);
    }
}
