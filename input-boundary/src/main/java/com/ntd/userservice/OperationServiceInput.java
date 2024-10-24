package com.ntd.userservice;

import com.ntd.userservice.dto.OperationInputDTO;
import com.ntd.userservice.dto.OperationParamsInputDTO;
import com.ntd.userservice.dto.OperationResultInputDTO;
import java.util.List;

public interface OperationServiceInput {
    OperationResultInputDTO executeOperation(String username, OperationParamsInputDTO params, String operationType);
    List<OperationInputDTO> getAllOperations();
}
