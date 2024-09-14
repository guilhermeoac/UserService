package com.ntd.userservice.controller;

import com.ntd.userservice.UserServiceInput;
import com.ntd.userservice.dto.UserBalanceInputDTO;
import com.ntd.userservice.exception.ApplicationException;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@OpenAPIDefinition(security = @SecurityRequirement(name = "JWT Access Token"))
@SecurityScheme(name = "JWT Access Token", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserServiceInput userServiceInput;

    public UserController(UserServiceInput userServiceInput) {
        this.userServiceInput = userServiceInput;
    }


    @GetMapping("/username")
    public ResponseEntity<UserBalanceInputDTO> getUserInfo(
            @RequestHeader("username") String username
    ) {
        var response = userServiceInput.findUserBalanceInfo(username);
        if (response.isEmpty()) throw new ApplicationException("resource.not.found", "User do not exist", HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(response.get());
    }
}


