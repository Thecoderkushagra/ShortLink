package com.TheCoderKushagra.controller;

import com.TheCoderKushagra.dto.ScrollResponse;
import com.TheCoderKushagra.dto.UrlResponseDto;
import com.TheCoderKushagra.entity.AppUser;
import com.TheCoderKushagra.service.Base62;
import com.TheCoderKushagra.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/url")
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createShortCode(@RequestParam("enter-url") String Url) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = (AppUser) authentication.getPrincipal();

        UrlResponseDto response = urlService.saveUrl(Url, user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(Map.of("response", response));
    }

    @GetMapping("/get")
    public ResponseEntity<ScrollResponse<?>> getAllUrl(
            @RequestParam(name = "cursor", defaultValue = "0") Long cursor,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = (AppUser) authentication.getPrincipal();
        ScrollResponse<UrlResponseDto> allUrls = urlService.getAllUrls(user.getId(), cursor, size);
        return ResponseEntity.ok(allUrls);
    }

    @DeleteMapping("/delete/{sc}")
    public ResponseEntity<?> deleteUrl(@PathVariable String sc) {
        SecurityContextHolder.getContext().getAuthentication();
        long id = Base62.decode(sc);
        String response = urlService.deleteUrl(id);
        return ResponseEntity.ok(Map.of("response", response));
    }
}
