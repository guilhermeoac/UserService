package com.ntd.userservice.repository.impl;

import com.ntd.userservice.repository.BalanceHistoryRepository;
import com.ntd.userservice.repository.dto.BalanceHistoryOutputDTO;
import com.ntd.userservice.repository.dto.BalanceOutputDTO;
import com.ntd.userservice.repository.dto.UserOutputDTO;
import com.ntd.userservice.repository.interfaces.BalanceJpaRepository;
import com.ntd.userservice.repository.model.BalanceEntity;
import com.ntd.userservice.repository.model.UserEntity;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BalanceRepositoryImplTest {

    @Mock
    private BalanceJpaRepository balanceJpaRepository;

    @Mock
    private BalanceHistoryRepository balanceHistoryRepository;

    @InjectMocks
    private BalanceRepositoryImpl balanceRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindBalanceByUser() {
        Long userId = 1L;
        BalanceEntity balanceEntity = new BalanceEntity(1L, BigDecimal.TEN, 1L, new UserEntity(userId, null, null, null));
        when(balanceJpaRepository.findBalanceEntityByUser_Id(userId)).thenReturn(Optional.of(balanceEntity));

        Optional<BalanceOutputDTO> result = balanceRepository.findBalanceByUser(userId);

        assertTrue(result.isPresent());
        assertEquals(BigDecimal.TEN, result.get().balance());
        verify(balanceJpaRepository, times(1)).findBalanceEntityByUser_Id(userId);
    }

    @Test
    void testFindBalanceByUser_NotFound() {
        Long userId = 1L;
        when(balanceJpaRepository.findBalanceEntityByUser_Id(userId)).thenReturn(Optional.empty());

        Optional<BalanceOutputDTO> result = balanceRepository.findBalanceByUser(userId);

        assertFalse(result.isPresent());
        verify(balanceJpaRepository, times(1)).findBalanceEntityByUser_Id(userId);
    }

    @Test
    void testSave_NewBalance() {
        BalanceOutputDTO dto = new BalanceOutputDTO(1L, new UserOutputDTO(1L, null, null, null), BigDecimal.TEN, 1L);
        BalanceEntity balanceEntity = new BalanceEntity(1L, BigDecimal.TEN, 1L, new UserEntity(1L, null, null, null));
        when(balanceJpaRepository.save(any(BalanceEntity.class))).thenReturn(balanceEntity);

        balanceRepository.save(dto, BigDecimal.ZERO);

        ArgumentCaptor<BalanceEntity> balanceCaptor = ArgumentCaptor.forClass(BalanceEntity.class);
        ArgumentCaptor<BalanceHistoryOutputDTO> historyCaptor = ArgumentCaptor.forClass(BalanceHistoryOutputDTO.class);

        verify(balanceJpaRepository, times(1)).save(balanceCaptor.capture());
        verify(balanceHistoryRepository, times(1)).save(historyCaptor.capture());

        assertEquals(BigDecimal.TEN, balanceCaptor.getValue().getBalance());
        assertEquals(BigDecimal.ZERO, historyCaptor.getValue().oldBalance());
        assertEquals(BigDecimal.TEN, historyCaptor.getValue().newBalance());
    }

    @Test
    void testSave_UpdateBalance() {
        BalanceOutputDTO dto = new BalanceOutputDTO(1L, new UserOutputDTO(1L, null, null, null), BigDecimal.TEN, 1L);
        BalanceEntity existingBalanceEntity = new BalanceEntity(1L, BigDecimal.ONE, 1L, new UserEntity(1L, null, null, null));
        when(balanceJpaRepository.findBalanceEntityByUser_Id(1L)).thenReturn(Optional.of(existingBalanceEntity));
        when(balanceJpaRepository.save(any(BalanceEntity.class))).thenReturn(existingBalanceEntity);

        balanceRepository.save(dto, BigDecimal.ONE);

        ArgumentCaptor<BalanceEntity> balanceCaptor = ArgumentCaptor.forClass(BalanceEntity.class);
        ArgumentCaptor<BalanceHistoryOutputDTO> historyCaptor = ArgumentCaptor.forClass(BalanceHistoryOutputDTO.class);

        verify(balanceJpaRepository, times(1)).save(balanceCaptor.capture());
        verify(balanceHistoryRepository, times(1)).save(historyCaptor.capture());

        assertEquals(BigDecimal.TEN, balanceCaptor.getValue().getBalance());
        assertEquals(BigDecimal.ONE, historyCaptor.getValue().oldBalance());
        assertEquals(BigDecimal.TEN, historyCaptor.getValue().newBalance());
    }
}
