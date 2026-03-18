package com.TheCoderKushagra.controller;

import com.TheCoderKushagra.service.Base62;
import com.TheCoderKushagra.service.CounterService;
import com.TheCoderKushagra.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("r")
@RequiredArgsConstructor
public class RedirectController {
    private final UrlService urlService;
    private final CounterService counterService;

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = urlService.getUrlByShortCode(shortCode);
        if (originalUrl == null) return ResponseEntity.notFound().build();

        long id = Base62.decode(shortCode);
        counterService.incrementClick(id);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
