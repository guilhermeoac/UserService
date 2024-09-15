package com.ntd.userservice.recordservice.dto;

import java.math.BigDecimal;

public record RecordOutputDTO(

        Long userId,
        String operationType,
        BigDecimal amount,
        BigDecimal cost,
        String operationResult
) {

}
