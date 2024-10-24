package com.ntd.userservice.operationservice;

import com.ntd.userservice.operationservice.dto.OperationDTO;
import com.ntd.userservice.operationservice.dto.OperationParamsDTO;
import com.ntd.userservice.operationservice.dto.OperationResultDTO;
import java.util.List;

public interface OperationService {

    OperationResultDTO executeOperation(Long userId, String operationType, OperationParamsDTO params);
    OperationDTO operationDetails(String operationType);
    List<OperationDTO> getAllOperations();
}
