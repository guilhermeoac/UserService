package com.ntd.userservice;

import com.ntd.userservice.exception.OutputException;
import com.ntd.userservice.repository.BalanceRepository;
import com.ntd.userservice.repository.UserRepository;
import com.ntd.userservice.repository.dto.BalanceOutputDTO;
import com.ntd.userservice.repository.dto.UserBalanceOutputDTO;
import com.ntd.userservice.repository.dto.UserOutputDTO;
import java.math.BigDecimal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BalanceUseCase implements BalanceServiceInput {
    protected final Log logger = LogFactory.getLog(this.getClass());

    private final BalanceRepository balanceRepository;
    private final UserRepository userRepository;

    public BalanceUseCase(BalanceRepository balanceRepository, UserRepository userRepository) {
        this.balanceRepository = balanceRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void increaseBalanceValue(String username, BigDecimal value) {
        try {
            validateInputValue(value);

            var userBalance = getUserBalanceOutputDTO(username);

            var newBalanceValue = userBalance.balance().add(value);

            validadeFinalBalanceValue(newBalanceValue);

            saveBalance(userBalance, newBalanceValue);
        } catch (Exception e) {
            logger.error("BalanceUseCase.increaseBalanceValue, message:" + e.getMessage(), e);
            throw e;
        }
    }

    private void validadeFinalBalanceValue(BigDecimal newBalanceValue) {
        if (newBalanceValue.compareTo(BigDecimal.ZERO) < 0) {
            throw new OutputException("balance.not.available", "Account has no balance available", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateInputValue(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new OutputException("balance.value.invalid", "Operation value must be positive", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public void decreaseBalanceValue(String username, BigDecimal value) {
        try {
            validateInputValue(value);

            var userBalance = getUserBalanceOutputDTO(username);

            var newBalanceValue = userBalance.balance().subtract(value);

            validadeFinalBalanceValue(newBalanceValue);

            saveBalance(userBalance, newBalanceValue);
        } catch (Exception e) {
            logger.error("BalanceUseCase.decreaseBalanceValue, message:" + e.getMessage(), e);
            throw e;
        }
    }

    private void saveBalance(UserBalanceOutputDTO userBalance, BigDecimal newBalanceValue) {
        balanceRepository.save(new BalanceOutputDTO(userBalance.balanceId(), new UserOutputDTO(userBalance.userId(), null, null, null), newBalanceValue, userBalance.version()), userBalance.balance());
    }

    private UserBalanceOutputDTO getUserBalanceOutputDTO(String username) {
        var userBalance = userRepository.findUserBalanceInfo(username);

        if (userBalance.isEmpty()) throw new OutputException("balance.not.found", "Balance not found", HttpStatus.BAD_REQUEST);
        return userBalance.get();
    }
}
