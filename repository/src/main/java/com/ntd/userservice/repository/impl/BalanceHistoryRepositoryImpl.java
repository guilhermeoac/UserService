package com.ntd.userservice.repository.impl;

import com.ntd.userservice.repository.BalanceHistoryRepository;
import com.ntd.userservice.repository.dto.BalanceHistoryOutputDTO;
import com.ntd.userservice.repository.interfaces.BalanceHistoryJpaRepository;
import com.ntd.userservice.repository.model.BalanceEntity;
import com.ntd.userservice.repository.model.BalanceEntityHistory;
import org.springframework.stereotype.Service;

@Service
public class BalanceHistoryRepositoryImpl implements BalanceHistoryRepository {

    private final BalanceHistoryJpaRepository balanceHistoryJpaRepository;

    public BalanceHistoryRepositoryImpl(BalanceHistoryJpaRepository balanceHistoryJpaRepository) {
        this.balanceHistoryJpaRepository = balanceHistoryJpaRepository;
    }

    @Override
    public void save(BalanceHistoryOutputDTO dto) {
        balanceHistoryJpaRepository.save(new BalanceEntityHistory(dto.id(), dto.oldBalance(), dto.newBalance(), new BalanceEntity(dto.balance().id(),null, dto.balance().version(), null), null));
    }
}