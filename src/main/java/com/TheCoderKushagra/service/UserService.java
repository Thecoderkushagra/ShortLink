package com.TheCoderKushagra.service;

import com.TheCoderKushagra.dto.UserRequestDto;
import com.TheCoderKushagra.dto.UserResponseDto;
import com.TheCoderKushagra.entity.AppUser;
import com.TheCoderKushagra.repository.UserRepository;
import com.TheCoderKushagra.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserResponseDto saveUser(UserRequestDto user) {
        AppUser appUser = AppUser.builder()
                .username(user.username())
                .password(passwordEncoder.encode(user.password()))
                .roles(Collections.singletonList("USER"))
                .build();
        AppUser save = userRepository.save(appUser);
        return UserResponseDto.builder()
                .id(save.getId())
                .name(save.getUsername())
                .roles(save.getRoles())
                .build();
    }

    public UserResponseDto getUserById(Long id) {
        AppUser byId = userRepository.findById(id).orElse(null);
        if (byId == null) {return null;}
        return UserResponseDto.builder()
                .id(byId.getId())
                .name(byId.getUsername())
                .roles(byId.getRoles())
                .build();
    }

    public String generateJwtToken(UserRequestDto user) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.username(), user.password())
        );
        AppUser principle = (AppUser) authenticate.getPrincipal();
        return jwtUtils.generateToken(principle);
    }
}
