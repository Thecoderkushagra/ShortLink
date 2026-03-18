package com.TheCoderKushagra.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UrlResponseDto(String shortCode, String url, Long clickCount, LocalDateTime expiresAt) {}
