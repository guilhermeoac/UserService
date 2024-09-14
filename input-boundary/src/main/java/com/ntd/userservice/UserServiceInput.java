package com.ntd.userservice;

import com.ntd.userservice.dto.UserBalanceInputDTO;
import java.util.Optional;

public interface UserServiceInput {

    Optional<UserBalanceInputDTO> findUserBalanceInfo(String username);
}
