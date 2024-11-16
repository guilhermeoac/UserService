package com.ntd.userservice;

import com.ntd.userservice.dto.CustomPageImpl;
import com.ntd.userservice.dto.PageResponse;
import com.ntd.userservice.recordservice.RecordService;
import com.ntd.userservice.recordservice.dto.RecordFilterOutputDTO;
import com.ntd.userservice.recordservice.dto.RecordOutputDTO;
import com.ntd.userservice.recordservice.dto.RecordResponseOutputDTO;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class RecordServiceImpl implements RecordService {
    private final RestTemplate restTemplate;
    protected final Log logger = LogFactory.getLog(this.getClass());

    public RecordServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Page<RecordResponseOutputDTO> findRecordsPageable(Long userId, RecordFilterOutputDTO dto) {
        try {
            var uri = UriComponentsBuilder.fromUriString("http://recordservice:8080/api/v1/record");
            uri.queryParamIfPresent("operationType", Optional.ofNullable(dto.operationType()));
            uri.queryParamIfPresent("amount", Optional.ofNullable(dto.amount()));
            uri.queryParamIfPresent("cost", Optional.ofNullable(dto.cost()));
            uri.queryParamIfPresent("operationResult", Optional.ofNullable(dto.operationResult()));
            uri.queryParamIfPresent("beginDate", Optional.ofNullable(dto.beginDate()));
            uri.queryParamIfPresent("endDate", Optional.ofNullable(dto.endDate()));
            uri.queryParamIfPresent("pageNumber", Optional.ofNullable(dto.pageNumber()));
            uri.queryParamIfPresent("pageSize", Optional.ofNullable(dto.pageSize()));
            uri.queryParamIfPresent("sortField", Optional.ofNullable(dto.sortField()));
            uri.queryParamIfPresent("sortDirection", Optional.ofNullable(dto.sortDirection()));

            var headers = new HttpHeaders();
            headers.add("userId", userId.toString());

            var response = restTemplate.exchange(
                    uri.toUriString(),
                    HttpMethod.GET,
                    new HttpEntity<>(null, headers),
                    new ParameterizedTypeReference<PageResponse<RecordResponseOutputDTO>>(){}).getBody();

            return response;
        } catch( Exception e) {
            logger.error("RecordServiceImpl.findRecordsPageable, message: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void save(RecordOutputDTO dto) {
        try {
            var uri = UriComponentsBuilder.fromUriString("http://recordservice:8080/api/v1/record");

            var headers = new HttpHeaders();
            headers.add("userId", dto.userId().toString());

            restTemplate.exchange(
                    uri.toUriString(),
                    HttpMethod.POST,
                    new HttpEntity<>(dto, headers),
                   String.class);
        } catch( Exception e) {
            logger.error("RecordServiceImpl.save, message: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void delete(Long id) {
        try {
            var uri = UriComponentsBuilder.fromUriString("http://recordservice:8080/api/v1/record");
            uri.queryParam("id", id.toString());

            var headers = new HttpHeaders();

            restTemplate.exchange(
                    uri.toUriString(),
                    HttpMethod.DELETE,
                    new HttpEntity<>(null, headers),
                    String.class);
        } catch( Exception e) {
            logger.error("RecordServiceImpl.delete, message: " + e.getMessage(), e);
            throw e;
        }
    }
}


