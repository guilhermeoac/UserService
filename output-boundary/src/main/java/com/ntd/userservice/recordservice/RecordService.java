package com.ntd.userservice.recordservice;

import com.ntd.userservice.recordservice.dto.RecordFilterOutputDTO;
import com.ntd.userservice.recordservice.dto.RecordOutputDTO;
import com.ntd.userservice.recordservice.dto.RecordResponseOutputDTO;
import org.springframework.data.domain.Page;

public interface RecordService {

    Page<RecordResponseOutputDTO> findRecordsPageable(Long userId, RecordFilterOutputDTO dto);

    void save(RecordOutputDTO dto);

}

