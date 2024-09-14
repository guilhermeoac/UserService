package com.ntd.userservice.auth;


import com.ntd.userservice.auth.dto.SigninRequest;

public interface AuthenticationService {
    String signin(SigninRequest request);

    void signUp(SigninRequest request);
}