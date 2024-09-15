package com.ntd.userservice;

import com.ntd.userservice.dto.RecordFilterInputDTO;
import com.ntd.userservice.dto.RecordInputDTO;
import org.springframework.data.domain.Page;

public interface RecordServiceInput {

    Page<RecordInputDTO> findRecordsPageable(String username, RecordFilterInputDTO dto);


}

