package com.ntd.userservice.repository;

import com.ntd.userservice.repository.dto.BalanceOutputDTO;
import java.math.BigDecimal;
import java.util.Optional;

public interface BalanceRepository {

    Optional<BalanceOutputDTO> findBalanceByUser(Long userId);

    void save(BalanceOutputDTO dto, BigDecimal oldValue);
}
