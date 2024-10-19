package com.ntd.userservice.controller;

import com.ntd.userservice.RecordServiceInput;
import com.ntd.userservice.dto.RecordFilterInputDTO;
import com.ntd.userservice.dto.RecordInputDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/record")
public class RecordController {

    private final RecordServiceInput recordServiceInput;

    public RecordController(RecordServiceInput recordServiceInput) {
        this.recordServiceInput = recordServiceInput;
    }

    @GetMapping
    public ResponseEntity<Page<RecordInputDTO>> getaAllPage(
            @RequestHeader("username") String username,
            @RequestParam(value = "operationType", required = false) String operationType,
            @RequestParam(value = "amount", required = false) BigDecimal amount,
            @RequestParam(value = "cost", required = false) BigDecimal cost,
            @RequestParam(value = "operationResult", required = false) String operationResult,
            @RequestParam(value = "beginDate", required = false) String beginDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "sortField", required = false) String sortField,
            @RequestParam(value = "sortDirection", required = false) String sortDirection
    ) throws Exception {
        return ResponseEntity.ok(recordServiceInput.findRecordsPageable(username, new RecordFilterInputDTO(
                operationType,
                amount,
                cost,
                operationResult,
                beginDate != null ? LocalDate.parse(beginDate, DateTimeFormatter.ISO_LOCAL_DATE).atTime(0, 0, 0) : null,
                endDate != null ? LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE).atTime(23, 59, 59) : null,
                pageNumber != null ? pageNumber : 0,
                pageSize != null ? pageSize : 10,
                sortField,
                sortDirection)));
    }
}


