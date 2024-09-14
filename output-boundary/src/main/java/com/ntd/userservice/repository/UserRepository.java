package com.ntd.userservice.repository;

import com.ntd.userservice.repository.dto.UserBalanceOutputDTO;
import com.ntd.userservice.repository.dto.UserOutputDTO;
import java.util.Optional;

public interface UserRepository {

    Optional<UserOutputDTO> findUserByUsername(String Username);

    void save(String username, String password);

    Optional<UserBalanceOutputDTO> findUserBalanceInfo(String username);
}
