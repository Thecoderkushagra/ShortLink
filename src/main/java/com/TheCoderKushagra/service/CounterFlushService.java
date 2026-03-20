package com.TheCoderKushagra.service;

import com.TheCoderKushagra.repository.UrlRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CounterFlushService {
    private final RedisTemplate<String, Long> redisTemplate;
    private final UrlRepository urlRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void flushCounters() {
        Set<String> keys = redisTemplate.keys("url:*:clicks");
        for (String key : keys) {
            Long count = redisTemplate.opsForValue().getAndSet(key, 0L);
            if (count == null || count == 0) continue;
            long id = extractId(key);
            urlRepository.incrementClickCount(id, count);
            redisTemplate.delete(key);
        }
    }

    private long extractId(String key) {
        String[] parts = key.split(":");
        return Long.parseLong(parts[1]);
    }
}
