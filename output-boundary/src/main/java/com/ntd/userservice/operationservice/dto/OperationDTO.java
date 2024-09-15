package com.ntd.userservice.operationservice.dto;


import java.math.BigDecimal;

public record OperationDTO(
        String type,
        BigDecimal cost
){
}



