package com.TheCoderKushagra.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
public record ScrollResponse<T> (
        List<T> items,
        Long scrollId,
        boolean hasNext,
        int pageSize
) {}

