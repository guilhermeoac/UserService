package com.ntd.userservice.repository.dto;

import java.math.BigDecimal;

public record BalanceOutputDTO(
        Long id, UserOutputDTO user, BigDecimal balance, Long version
) {
}
