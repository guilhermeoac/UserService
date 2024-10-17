package com.ntd.userservice.repository.impl;

import com.ntd.userservice.repository.BalanceRepository;
import com.ntd.userservice.repository.dto.BalanceOutputDTO;
import com.ntd.userservice.repository.dto.UserOutputDTO;
import com.ntd.userservice.repository.interfaces.UserJpaRepository;
import com.ntd.userservice.repository.model.UserEntity;
import java.math.BigDecimal;
import java.util.List;
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

class UserRepositoryImplTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private BalanceRepository balanceRepository;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindUserByUsername() {
        String username = "testUser";
        UserEntity userEntity = new UserEntity(1L, username, "password", "DEFAULT");
        when(userJpaRepository.findUserEntityByUsername(username)).thenReturn(List.of(userEntity));

        Optional<UserOutputDTO> result = userRepository.findUserByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(username, result.get().username());
        verify(userJpaRepository, times(1)).findUserEntityByUsername(username);
    }

    @Test
    void testFindUserByUsername_NotFound() {
        String username = "testUser";
        when(userJpaRepository.findUserEntityByUsername(username)).thenReturn(List.of());

        Optional<UserOutputDTO> result = userRepository.findUserByUsername(username);

        assertFalse(result.isPresent());
        verify(userJpaRepository, times(1)).findUserEntityByUsername(username);
    }

    @Test
    void testSave_NewUser() {
        String username = "newUser";
        String password = "newPassword";
        UserEntity userEntity = new UserEntity(1L, username, password, "DEFAULT");
        when(userJpaRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        userRepository.save(username, password);

        ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        ArgumentCaptor<BalanceOutputDTO> balanceCaptor = ArgumentCaptor.forClass(BalanceOutputDTO.class);

        verify(userJpaRepository, times(1)).save(userCaptor.capture());
        verify(balanceRepository, times(1)).save(balanceCaptor.capture(), any());

        assertEquals(username, userCaptor.getValue().getUsername());
        assertEquals(new BigDecimal("100"), balanceCaptor.getValue().balance());
    }
}
