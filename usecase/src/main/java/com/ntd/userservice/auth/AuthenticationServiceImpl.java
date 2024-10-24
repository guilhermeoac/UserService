package com.ntd.userservice.auth;

import com.ntd.userservice.auth.dto.SigninRequest;
import com.ntd.userservice.auth.dto.User;
import com.ntd.userservice.exception.ApplicationException;
import com.ntd.userservice.exception.OutputException;
import com.ntd.userservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    public AuthenticationServiceImpl(PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @Override
    public String signin(SigninRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUser(), request.getPassword()));
            var userOutputDTO = userRepository.findUserByUsername(request.getUser());
            if (userOutputDTO.isEmpty()) throw new OutputException("user.not.found", "User do not exist!", HttpStatus.BAD_REQUEST);
            return jwtService.generateToken(new User(userOutputDTO.get().id(), userOutputDTO.get().username(), userOutputDTO.get().password()));
        } catch (Exception e) {
            throw new ApplicationException("invalid.password", "User or password invalid!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void signUp(SigninRequest request) {
        userRepository.save(request.getUser(), new BCryptPasswordEncoder().encode(request.getPassword()));
    }
}