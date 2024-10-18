package com.ntd.userservice;

import com.google.gson.Gson;
import com.ntd.userservice.dto.ErrorResponse;
import com.ntd.userservice.exception.OutputException;
import com.ntd.userservice.operationservice.OperationService;
import com.ntd.userservice.operationservice.dto.OperationDTO;
import com.ntd.userservice.operationservice.dto.OperationParamsDTO;
import com.ntd.userservice.operationservice.dto.OperationResultDTO;
import java.util.List;
import java.util.Objects;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;



@Service
public class OperationServiceImpl implements OperationService {
    private final RestTemplate restTemplate;
    protected final Log logger = LogFactory.getLog(this.getClass());

    public OperationServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public OperationResultDTO executeOperation(Long userId, String operationType, OperationParamsDTO params) {
        try {

            var headers = new HttpHeaders();
            headers.add("userId", userId.toString());
            headers.add("type", operationType);


            return Objects.requireNonNull(restTemplate.exchange(
                    "https://operationservice.onrender.com/api/v1/operation/execute",
                    HttpMethod.POST,
                    new HttpEntity<>(params, headers),
                    OperationResultDTO.class).getBody());
        } catch( HttpClientErrorException e) {
            logger.error("executeOperation, message: " + e.getMessage(), e);
            if (HttpStatus.NOT_ACCEPTABLE == HttpStatus.valueOf(e.getStatusCode().value())) {
                var code = new Gson().fromJson(e.getResponseBodyAsString(), ErrorResponse.class);
                throw new OutputException(code.getCode(), code.getMessage(), HttpStatus.valueOf(e.getStatusCode().value()));
            }
            throw e;
        } catch( Exception e) {
            logger.error("executeOperation, message: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public OperationDTO operationDetails(String operationType) {
        try {

            var headers = new HttpHeaders();
            headers.add("type", operationType);


            return Objects.requireNonNull(restTemplate.exchange(
                    "https://operationservice.onrender.com/api/v1/operation/type",
                    HttpMethod.GET,
                    new HttpEntity<>(null, headers),
                    OperationDTO.class).getBody());
        } catch( Exception e) {
            logger.error("operationDetails, message: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<OperationDTO> getAllOperations(String operationType) {
        try {

            return Objects.requireNonNull(restTemplate.exchange(
                    "https://operationservice.onrender.com/api/v1/operation",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<OperationDTO>>(){}).getBody());
        } catch( Exception e) {
            logger.error("getAllOperations, message: " + e.getMessage(), e);
            throw e;
        }
    }
}


