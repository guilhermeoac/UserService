package com.ntd.userservice;

import com.ntd.userservice.dto.UserBalanceInputDTO;
import com.ntd.userservice.repository.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserUseCase implements UserServiceInput {

    private final UserRepository userRepository;

    public UserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserBalanceInputDTO> findUserBalanceInfo(String username) {
        return userRepository.findUserBalanceInfo(username).map(it -> new UserBalanceInputDTO(it.userId(), it.username(), it.balance()));
    }
}
