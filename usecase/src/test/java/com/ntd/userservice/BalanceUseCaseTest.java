package com.ntd.userservice;

import com.ntd.userservice.exception.OutputException;
import com.ntd.userservice.repository.BalanceRepository;
import com.ntd.userservice.repository.UserRepository;
import com.ntd.userservice.repository.dto.BalanceOutputDTO;
import com.ntd.userservice.repository.dto.UserBalanceOutputDTO;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BalanceUseCaseTest {

    @Mock
    private BalanceRepository balanceRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BalanceUseCase balanceUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIncreaseBalanceValue_Success() {
        String username = "testUser";
        BigDecimal value = BigDecimal.TEN;
        UserBalanceOutputDTO userBalance = new UserBalanceOutputDTO(1L, "1", BigDecimal.ZERO,1L, 1L);
        when(userRepository.findUserBalanceInfo(username)).thenReturn(Optional.of(userBalance));

        balanceUseCase.increaseBalanceValue(username, value);

        ArgumentCaptor<BalanceOutputDTO> balanceCaptor = ArgumentCaptor.forClass(BalanceOutputDTO.class);
        verify(balanceRepository, times(1)).save(balanceCaptor.capture(), any());
        assertEquals(BigDecimal.TEN, balanceCaptor.getValue().balance());
    }

    @Test
    void testIncreaseBalanceValue_InvalidInput() {
        String username = "testUser";
        BigDecimal value = BigDecimal.valueOf(-10);

        OutputException exception = assertThrows(OutputException.class, () -> balanceUseCase.increaseBalanceValue(username, value));

        assertEquals("balance.value.invalid", exception.getCode());
        assertEquals("Operation value must be positive", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(balanceRepository, never()).save(any(BalanceOutputDTO.class), any());
    }

    @Test
    void testIncreaseBalanceValue_UserNotFound() {
        String username = "testUser";
        BigDecimal value = BigDecimal.TEN;
        when(userRepository.findUserBalanceInfo(username)).thenReturn(Optional.empty());

        OutputException exception = assertThrows(OutputException.class, () -> balanceUseCase.increaseBalanceValue(username, value));

        assertEquals("balance.not.found", exception.getCode());
        assertEquals("Balance not found", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(balanceRepository, never()).save(any(BalanceOutputDTO.class), any());
    }

    @Test
    void testDecreaseBalanceValue_Success() {
        String username = "testUser";
        BigDecimal value = BigDecimal.TEN;
        UserBalanceOutputDTO userBalance = new UserBalanceOutputDTO(1L, "1", BigDecimal.valueOf(20),1L, 1L);
        when(userRepository.findUserBalanceInfo(username)).thenReturn(Optional.of(userBalance));

        balanceUseCase.decreaseBalanceValue(username, value);

        ArgumentCaptor<BalanceOutputDTO> balanceCaptor = ArgumentCaptor.forClass(BalanceOutputDTO.class);
        verify(balanceRepository, times(1)).save(balanceCaptor.capture(), any());
        assertEquals(BigDecimal.TEN, balanceCaptor.getValue().balance());
    }

    @Test
    void testDecreaseBalanceValue_InvalidInput() {
        String username = "testUser";
        BigDecimal value = BigDecimal.valueOf(-10);

        OutputException exception = assertThrows(OutputException.class, () -> balanceUseCase.decreaseBalanceValue(username, value));

        assertEquals("balance.value.invalid", exception.getCode());
        assertEquals("Operation value must be positive", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(balanceRepository, never()).save(any(BalanceOutputDTO.class), any());
    }

    @Test
    void testDecreaseBalanceValue_UserNotFound() {
        String username = "testUser";
        BigDecimal value = BigDecimal.TEN;
        when(userRepository.findUserBalanceInfo(username)).thenReturn(Optional.empty());

        OutputException exception = assertThrows(OutputException.class, () -> balanceUseCase.decreaseBalanceValue(username, value));

        assertEquals("balance.not.found", exception.getCode());
        assertEquals("Balance not found", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(balanceRepository, never()).save(any(BalanceOutputDTO.class), any());
    }

    @Test
    void testDecreaseBalanceValue_InsufficientBalance() {
        String username = "testUser";
        BigDecimal value = BigDecimal.TEN;
        UserBalanceOutputDTO userBalance = new UserBalanceOutputDTO(1L, "1", BigDecimal.valueOf(5), 1L, 1L);
        when(userRepository.findUserBalanceInfo(username)).thenReturn(Optional.of(userBalance));

        OutputException exception = assertThrows(OutputException.class, () -> balanceUseCase.decreaseBalanceValue(username, value));

        assertEquals("balance.not.available", exception.getCode());
        assertEquals("Account has no balance available", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(balanceRepository, never()).save(any(BalanceOutputDTO.class), any());
    }
}
