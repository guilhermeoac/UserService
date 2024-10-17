package com.ntd.userservice.repository.impl;

import com.ntd.userservice.repository.dto.BalanceHistoryOutputDTO;
import com.ntd.userservice.repository.dto.BalanceOutputDTO;
import com.ntd.userservice.repository.interfaces.BalanceHistoryJpaRepository;
import com.ntd.userservice.repository.model.BalanceEntityHistory;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class BalanceHistoryRepositoryImplTest {

    @Mock
    private BalanceHistoryJpaRepository balanceHistoryJpaRepository;

    @InjectMocks
    private BalanceHistoryRepositoryImpl balanceHistoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        BalanceOutputDTO balanceOutputDTO = new BalanceOutputDTO(1L, null, BigDecimal.TEN, 1L);
        BalanceHistoryOutputDTO dto = new BalanceHistoryOutputDTO(1L, balanceOutputDTO, BigDecimal.ONE, BigDecimal.TEN, null);

        balanceHistoryRepository.save(dto);

        ArgumentCaptor<BalanceEntityHistory> captor = ArgumentCaptor.forClass(BalanceEntityHistory.class);
        verify(balanceHistoryJpaRepository, times(1)).save(captor.capture());

        BalanceEntityHistory savedEntity = captor.getValue();
        assertEquals(dto.id(), savedEntity.getId());
        assertEquals(dto.oldBalance(), savedEntity.getOldBalance());
        assertEquals(dto.newBalance(), savedEntity.getNewBalance());
        assertEquals(dto.balance().id(), savedEntity.getBalance().getId());
    }
}
