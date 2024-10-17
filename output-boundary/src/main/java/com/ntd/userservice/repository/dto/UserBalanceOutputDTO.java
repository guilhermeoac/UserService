package com.ntd.userservice.repository.dto;

import java.math.BigDecimal;

public record UserBalanceOutputDTO(
        Long userId, String username, BigDecimal balance, Long balanceId, Long version
) {
}
