package com.ntd.userservice.repository.impl;

import com.ntd.userservice.repository.BalanceHistoryRepository;
import com.ntd.userservice.repository.BalanceRepository;
import com.ntd.userservice.repository.dto.BalanceHistoryOutputDTO;
import com.ntd.userservice.repository.dto.BalanceOutputDTO;
import com.ntd.userservice.repository.dto.UserOutputDTO;
import com.ntd.userservice.repository.interfaces.BalanceJpaRepository;
import com.ntd.userservice.repository.model.BalanceEntity;
import com.ntd.userservice.repository.model.UserEntity;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BalanceRepositoryImpl implements BalanceRepository {

    private final BalanceJpaRepository balanceJpaRepository;

    private final BalanceHistoryRepository balanceHistoryRepository;

    public BalanceRepositoryImpl(BalanceJpaRepository balanceJpaRepository, BalanceHistoryRepository balanceHistoryRepository) {
        this.balanceJpaRepository = balanceJpaRepository;
        this.balanceHistoryRepository = balanceHistoryRepository;
    }

    @Override
    public Optional<BalanceOutputDTO> findBalanceByUser(Long userId) {
        return balanceJpaRepository.findBalanceEntityByUser_Id(userId).map(it -> new BalanceOutputDTO(it.getId(), new UserOutputDTO(it.getUser().getId(), null, null, null), it.getBalance()));
    }

    @Override
    @Transactional
    public void save(BalanceOutputDTO dto) {
        var oldBalance = findBalanceByUser(dto.user().id());
        BigDecimal oldBalanceValue = BigDecimal.ZERO;
        if (oldBalance.isPresent()) {
            oldBalanceValue = oldBalance.get().balance();
        }
        var balanceEntity = balanceJpaRepository.save(new BalanceEntity(dto.id(), dto.balance(), new UserEntity(dto.user().id(), null, null, null)));
        balanceHistoryRepository.save(new BalanceHistoryOutputDTO(null, new BalanceOutputDTO(balanceEntity.getId(), null, null), oldBalanceValue, dto.balance(), null));
    }
}