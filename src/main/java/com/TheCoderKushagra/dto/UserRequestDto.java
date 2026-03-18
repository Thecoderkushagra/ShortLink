package com.TheCoderKushagra.dto;

public record UserRequestDto(String username, String password) {
    public UserRequestDto {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be blank");
        }
    }
}
