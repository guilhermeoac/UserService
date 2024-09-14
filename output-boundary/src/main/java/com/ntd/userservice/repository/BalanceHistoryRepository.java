package com.ntd.userservice.repository;

import com.ntd.userservice.repository.dto.BalanceHistoryOutputDTO;

public interface BalanceHistoryRepository {

    void save(BalanceHistoryOutputDTO dto);
}
