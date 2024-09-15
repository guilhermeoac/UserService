package com.ntd.userservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RecordInputDTO(
        Long id,
        String operationType,
        BigDecimal amount,
        BigDecimal cost,
        String operationResult,
        LocalDateTime date
) {

}
