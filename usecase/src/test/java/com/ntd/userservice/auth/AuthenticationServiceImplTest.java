package com.ntd.userservice.auth;

import com.ntd.userservice.auth.dto.SigninRequest;
import com.ntd.userservice.auth.dto.User;
import com.ntd.userservice.exception.ApplicationException;
import com.ntd.userservice.exception.OutputException;
import com.ntd.userservice.repository.UserRepository;
import com.ntd.userservice.repository.dto.UserOutputDTO;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthenticationServiceImplTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignin_Success() {
        SigninRequest request = new SigninRequest("testUser", "testPassword");
        User user = new User(1L, "testUser", "encodedPassword");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepository.findUserByUsername("testUser")).thenReturn(Optional.of(new UserOutputDTO(1L, "testUser", "encodedPassword", "ROLE_USER")));
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        String token = authenticationService.signin(request);

        assertEquals("jwtToken", token);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findUserByUsername("testUser");
        verify(jwtService, times(1)).generateToken(any(User.class));
    }

    @Test
    void testSignin_UserNotFound() {
        SigninRequest request = new SigninRequest("testUser", "testPassword");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepository.findUserByUsername("testUser")).thenReturn(Optional.empty());

        ApplicationException exception = assertThrows(ApplicationException.class, () -> authenticationService.signin(request));

        assertEquals("invalid.password", exception.getCode());
        assertEquals("User or password invalid!", exception.getMessage());
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findUserByUsername("testUser");
        verify(jwtService, never()).generateToken(any(User.class));
    }

    @Test
    void testSignin_AuthenticationFailed() {
        SigninRequest request = new SigninRequest("testUser", "testPassword");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new RuntimeException("Authentication failed"));

        ApplicationException exception = assertThrows(ApplicationException.class, () -> authenticationService.signin(request));

        assertEquals("User or password invalid!", exception.getMessage());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, never()).findUserByUsername(anyString());
        verify(jwtService, never()).generateToken(any(User.class));
    }

    @Test
    void testSignUp() {
        SigninRequest request = new SigninRequest("newUser", "newPassword");

        authenticationService.signUp(request);

        ArgumentCaptor<String> usernameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);

        verify(userRepository, times(1)).save(usernameCaptor.capture(), passwordCaptor.capture());
        assertEquals("newUser", usernameCaptor.getValue());
        assertTrue(new BCryptPasswordEncoder().matches("newPassword", passwordCaptor.getValue()));
    }
}
