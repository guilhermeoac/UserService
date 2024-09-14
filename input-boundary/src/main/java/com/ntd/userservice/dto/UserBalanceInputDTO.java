package com.ntd.userservice.dto;

import java.math.BigDecimal;

public record UserBalanceInputDTO(
        Long userId, String username, BigDecimal balance
) {
}
