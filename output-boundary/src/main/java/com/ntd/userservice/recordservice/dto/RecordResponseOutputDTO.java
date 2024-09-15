package com.ntd.userservice.recordservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RecordResponseOutputDTO(
        Long id,
        String operationType,
        BigDecimal amount,
        BigDecimal cost,
        String operationResult,
        LocalDateTime date
) {

}
