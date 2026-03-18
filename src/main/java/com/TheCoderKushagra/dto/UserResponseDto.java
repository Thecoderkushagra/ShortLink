package com.TheCoderKushagra.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record UserResponseDto(Long id, String name, List<String> roles) {}