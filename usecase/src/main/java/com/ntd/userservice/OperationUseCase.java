package com.ntd.userservice;

import com.ntd.userservice.dto.OperationParamsInputDTO;
import com.ntd.userservice.dto.OperationResultInputDTO;
import com.ntd.userservice.exception.ApplicationException;
import com.ntd.userservice.exception.OutputException;
import com.ntd.userservice.operationservice.OperationService;
import com.ntd.userservice.operationservice.dto.OperationParamsDTO;
import com.ntd.userservice.recordservice.RecordService;
import com.ntd.userservice.recordservice.dto.RecordOutputDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OperationUseCase implements OperationServiceInput {
    protected final Log logger = LogFactory.getLog(this.getClass());

    private final BalanceServiceInput balanceServiceInput;
    private final OperationService operationService;
    private final UserServiceInput userServiceInput;
    private final RecordService recordService;

    public OperationUseCase(BalanceServiceInput balanceServiceInput, OperationService operationService, UserServiceInput userServiceInput, RecordService recordService) {
        this.balanceServiceInput = balanceServiceInput;
        this.operationService = operationService;
        this.userServiceInput = userServiceInput;
        this.recordService = recordService;
    }


    @Override
    @Transactional
    public OperationResultInputDTO executeOperation(String username, OperationParamsInputDTO params, String operationType) {
        try {

            var cost = operationService.operationDetails(operationType).cost();

            balanceServiceInput.decreaseBalanceValue(username, cost);

            var userInfo = userServiceInput.findUserBalanceInfo(username);

            var response = operationService.executeOperation(userInfo.get().userId(), operationType, new OperationParamsDTO(params.firstParam(), params.secondParam()));

            recordService.save(
                    new RecordOutputDTO(
                            userInfo.get().userId(),
                            operationType,
                            cost,
                            userInfo.get().balance(),
                            response.result()

                    )
            );

            return new OperationResultInputDTO(response.result());
        } catch (OutputException e) {
            logger.error("OperationUseCase.executeOperation, message:" + e.getMessage(), e);
            throw new ApplicationException(e.getCode(), e.getMessage(), e.getStatus());
        } catch (Exception e) {
            logger.error("OperationUseCase.executeOperation, message:" + e.getMessage(), e);
            throw e;
        }
    }
}

