package com.ntd.userservice.repository.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BalanceHistoryOutputDTO(
        Long id, BalanceOutputDTO balance, BigDecimal oldBalance, BigDecimal newBalance, LocalDateTime date
) {
}
