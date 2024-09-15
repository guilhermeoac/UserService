package com.ntd.userservice.exception;

import org.springframework.http.HttpStatus;

public class OutputException extends RuntimeException {

    private String code;
    private HttpStatus status;
    public OutputException(String code, String message, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }
}
