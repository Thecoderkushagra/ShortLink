package com.TheCoderKushagra.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CounterService {
    private final RedisTemplate<String, Long> redisTemplate;

    public void incrementClick(Long urlId) {
        String key = "url:" + urlId + ":clicks";
        redisTemplate.opsForValue().increment(key);
    }

    public long getClicks(Long urlId) {
        String key = "url:" + urlId + ":clicks";
        Long value = redisTemplate.opsForValue().get(key);
        return value == null ? 0 : value;
    }
}
