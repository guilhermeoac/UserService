package com.ntd.userservice.auth;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtServiceImplTest {

    @InjectMocks
    private JwtServiceImpl jwtService;

    @Mock
    private UserDetails userDetails;

    private String token;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDetails = new User("testUser", "password", new ArrayList<>());
        token = jwtService.generateToken(userDetails);
    }

    @Test
    void testExtractUserName() {
        String userName = jwtService.extractUserName(token);
        assertEquals("testUser", userName);
    }

    @Test
    void testGenerateToken() {
        String generatedToken = jwtService.generateToken(userDetails);
        assertNotNull(generatedToken);
    }

    @Test
    void testIsTokenValid() {
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

}
