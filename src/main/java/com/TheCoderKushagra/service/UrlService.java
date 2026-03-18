package com.TheCoderKushagra.service;

import com.TheCoderKushagra.dto.ScrollResponse;
import com.TheCoderKushagra.dto.UrlResponseDto;
import com.TheCoderKushagra.entity.AppUser;
import com.TheCoderKushagra.entity.UrlEntity;
import com.TheCoderKushagra.repository.UrlRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;

    @Transactional
    public UrlResponseDto saveUrl(String url, AppUser appUser) {
        UrlEntity urlBuild = UrlEntity.builder()
                .user(appUser)
                .url(url)
                .expiresAt(LocalDateTime.now().plusDays(30))
                .build();
        UrlEntity saved = urlRepository.save(urlBuild);

        return UrlResponseDto.builder()
                .shortCode(Base62.encode(saved.getId()))
                .url(saved.getUrl())
                .expiresAt(saved.getExpiresAt())
                .build();
    }

    public ScrollResponse<UrlResponseDto> getAllUrls(Long userId, Long cursor, int size) {
        Pageable pageable = PageRequest.of(0, size);
        List<UrlEntity> urlEntities = urlRepository.fetchNextPageByUser(userId, cursor, pageable);
        boolean hasNext = (urlEntities.size() == size);
        Long nextCursor = hasNext ? urlEntities.getLast().getId() : null;

        List<UrlResponseDto> list = urlEntities.stream().map(e -> UrlResponseDto.builder()
                .shortCode(Base62.encode(e.getId()))
                .url(e.getUrl())
                .clickCount(e.getClickCount())
                .expiresAt(e.getExpiresAt())
                .build()).toList();

        return ScrollResponse.<UrlResponseDto>builder()
                .items(list)
                .scrollId(nextCursor)
                .hasNext(hasNext)
                .pageSize(size)
                .build();
    }

    public String getUrlByShortCode(String shortCode) {
        Long id = Base62.decode(shortCode);
        UrlEntity urlEntity = urlRepository.findById(id).orElse(null);
        return (urlEntity != null) ? urlEntity.getUrl() : null;
    }

    public String deleteUrl(Long id) {
        int i = urlRepository.deleteUrlById(id);
        return (i == 1) ? "Deleted Successfully" : "key dose not exist";
    }

}
