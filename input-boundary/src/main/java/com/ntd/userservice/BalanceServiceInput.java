package com.ntd.userservice;

import java.math.BigDecimal;

public interface BalanceServiceInput {

    void increaseBalanceValue(String username, BigDecimal value);
    void decreaseBalanceValue(String username, BigDecimal value);
}
