package com.ntd.userservice.repository.dto;

public record UserOutputDTO(
        Long id, String username, String password, String role
) {
}
