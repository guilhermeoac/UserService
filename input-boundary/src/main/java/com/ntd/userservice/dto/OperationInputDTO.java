package com.ntd.userservice.dto;


import java.math.BigDecimal;

public record OperationInputDTO(
        String type,
        BigDecimal cost
){
}



