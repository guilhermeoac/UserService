package com.ntd.userservice;

import com.ntd.userservice.dto.RecordFilterInputDTO;
import com.ntd.userservice.dto.RecordInputDTO;
import com.ntd.userservice.exception.ApplicationException;
import com.ntd.userservice.exception.OutputException;
import com.ntd.userservice.operationservice.OperationService;
import com.ntd.userservice.recordservice.RecordService;
import com.ntd.userservice.recordservice.dto.RecordFilterOutputDTO;
import com.ntd.userservice.recordservice.dto.RecordOutputDTO;
import com.ntd.userservice.recordservice.dto.RecordResponseOutputDTO;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

@Service
public class RecordUseCase implements RecordServiceInput {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private final UserServiceInput userServiceInput;
    private final RecordService recordService;

    public RecordUseCase(UserServiceInput userServiceInput, RecordService recordService) {
        this.userServiceInput = userServiceInput;
        this.recordService = recordService;
    }

    @Override
    public Page<RecordInputDTO> findRecordsPageable(String username, RecordFilterInputDTO dto) {
        try {

            var userInfo = userServiceInput.findUserBalanceInfo(username);

            var response = recordService.findRecordsPageable(
                    userInfo.get().userId(),
                    new RecordFilterOutputDTO(
                            dto.operationType(),
                            dto.amount(),
                            dto.cost(),
                            dto.operationResult(),
                            dto.beginDate(),
                            dto.endDate(),
                            dto.pageNumber(),
                            dto.pageSize(),
                            dto.sortField(),
                            dto.sortDirection()
                            ));
            return new PageImpl<>(response.getContent().stream().map(it -> new RecordInputDTO(it.id(), it.operationType(), it.amount(), it.cost(), it.operationResult(), it.date())).collect(Collectors.toList()), response.getPageable(), response.getTotalElements());

        } catch (OutputException e) {
            logger.error("RecordUseCase.findRecordsPageable, message:" + e.getMessage(), e);
            throw new ApplicationException(e.getCode(), e.getMessage(), e.getStatus());
        } catch (Exception e) {
            logger.error("RecordUseCase.findRecordsPageable, message:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void deleteRecord(Long id) {
        try {
            recordService.delete(id);
        } catch (Exception e) {
            logger.error("RecordUseCase.deleteRecord, message:" + e.getMessage(), e);
            throw e;
        }
    }
}

