package com.ntd.userservice.recordservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RecordFilterOutputDTO(
        String operationType,
        BigDecimal amount,
        BigDecimal cost,
        String operationResult,
        LocalDateTime beginDate,
        LocalDateTime endDate,
        Integer pageNumber,
        Integer pageSize,
        String sortField,
        String sortDirection
) {

}
