package com.ntd.userservice.controller;

import com.ntd.userservice.BalanceServiceInput;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import java.math.BigDecimal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/balance")
@OpenAPIDefinition(security = @SecurityRequirement(name = "JWT Access Token"))
@SecurityScheme(name = "JWT Access Token", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
@SecurityRequirement(name = "bearerAuth")
public class BalanceController {

    private final BalanceServiceInput balanceServiceInput;

    public BalanceController(BalanceServiceInput balanceServiceInput) {
        this.balanceServiceInput = balanceServiceInput;
    }


    @PutMapping("/increase")
    public ResponseEntity<Void> increaseBalance(
            @RequestHeader("username") String username,
            @RequestHeader("value") BigDecimal value
    ) {
        balanceServiceInput.increaseBalanceValue(username, value);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/decrease")
    public ResponseEntity<Void> decreaseBalance(
            @RequestHeader("username") String username,
            @RequestHeader("value") BigDecimal value
    ) {
        balanceServiceInput.decreaseBalanceValue(username, value);
        return ResponseEntity.ok().build();
    }
}


