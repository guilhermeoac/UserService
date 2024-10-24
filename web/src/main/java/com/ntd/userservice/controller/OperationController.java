package com.ntd.userservice.controller;

import com.ntd.userservice.OperationServiceInput;
import com.ntd.userservice.dto.OperationInputDTO;
import com.ntd.userservice.dto.OperationParamsInputDTO;
import com.ntd.userservice.dto.OperationResultInputDTO;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/operation")
@OpenAPIDefinition(security = @SecurityRequirement(name = "JWT Access Token"))
@SecurityScheme(name = "JWT Access Token", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
@SecurityRequirement(name = "bearerAuth")
public class OperationController {

    private final OperationServiceInput operationServiceInput;

    public OperationController(OperationServiceInput operationServiceInput) {
        this.operationServiceInput = operationServiceInput;
    }


    @PostMapping()
    public ResponseEntity<OperationResultInputDTO> executeOperation(
            @RequestParam("type") String type,
            @RequestHeader("username") String username,
            @RequestBody OperationParamsInputDTO body
    ) {
        return ResponseEntity.ok(operationServiceInput.executeOperation(username, body, type));
    }


    @GetMapping()
    public ResponseEntity<List<OperationInputDTO>> getAllOperations(
    ) {
        return ResponseEntity.ok(operationServiceInput.getAllOperations());
    }
}


