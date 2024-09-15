package com.ntd.userservice.auth;

import com.ntd.userservice.auth.dto.Role;
import com.ntd.userservice.auth.dto.User;
import com.ntd.userservice.exception.OutputException;
import com.ntd.userservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {

                var userOutputDTO = userRepository.findUserByUsername(username);
                if (userOutputDTO.isEmpty()) throw new OutputException("user.not.found", "User do not exist!", HttpStatus.BAD_REQUEST);
                return new User(userOutputDTO.get().id(), userOutputDTO.get().username(), userOutputDTO.get().password(), Role.valueOf(userOutputDTO.get().role().toUpperCase()));
            }
        };
    }
}