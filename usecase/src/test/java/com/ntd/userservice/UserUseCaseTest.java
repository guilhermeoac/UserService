package com.ntd.userservice;

import com.ntd.userservice.dto.UserBalanceInputDTO;
import com.ntd.userservice.repository.UserRepository;
import com.ntd.userservice.repository.dto.UserBalanceOutputDTO;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindUserBalanceInfo_Success() {
        String username = "testUser";
        UserBalanceOutputDTO userBalanceOutputDTO = new UserBalanceOutputDTO(1L, username, BigDecimal.TEN, 1L, 1L);
        when(userRepository.findUserBalanceInfo(username)).thenReturn(Optional.of(userBalanceOutputDTO));

        Optional<UserBalanceInputDTO> result = userUseCase.findUserBalanceInfo(username);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().userId());
        assertEquals(username, result.get().username());
        assertEquals(BigDecimal.TEN, result.get().balance());
        verify(userRepository, times(1)).findUserBalanceInfo(username);
    }

    @Test
    void testFindUserBalanceInfo_UserNotFound() {
        String username = "testUser";
        when(userRepository.findUserBalanceInfo(username)).thenReturn(Optional.empty());

        Optional<UserBalanceInputDTO> result = userUseCase.findUserBalanceInfo(username);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findUserBalanceInfo(username);
    }
}
