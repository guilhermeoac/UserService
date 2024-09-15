package com.ntd.userservice;

import com.ntd.userservice.dto.OperationParamsInputDTO;
import com.ntd.userservice.dto.OperationResultInputDTO;

public interface OperationServiceInput {
    OperationResultInputDTO executeOperation(String username, OperationParamsInputDTO params, String operationType);
}
