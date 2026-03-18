package com.TheCoderKushagra.controller;

import com.TheCoderKushagra.dto.UserRequestDto;
import com.TheCoderKushagra.dto.UserResponseDto;
import com.TheCoderKushagra.entity.AppUser;
import com.TheCoderKushagra.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody UserRequestDto user) {
        UserResponseDto appUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(Map.of("message", "SignUp successfully!!",
                        "user", appUser));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserRequestDto user) {
        String token = userService.generateJwtToken(user);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(Map.of("message", "Login successfully!!",
                        "token", token));
    }
}
