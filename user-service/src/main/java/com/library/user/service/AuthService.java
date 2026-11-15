package com.library.user.service;

import com.library.user.common.ApiException;
import com.library.user.dto.AuthResponse;
import com.library.user.dto.LoginRequest;
import com.library.user.dto.RegisterRequest;
import com.library.user.dto.UserResponse;
import com.library.user.entity.Role;
import com.library.user.entity.User;
import com.library.user.repository.UserRepository;
import com.library.user.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthService(UserRepository repo, PasswordEncoder encoder, JwtService jwt) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    /** BR-11: الإيميل فريد والباسورد BCrypt. أي حد بيسجل بيبقى MEMBER. */
    public UserResponse register(RegisterRequest req) {
        String email = req.email().trim().toLowerCase();
        if (repo.existsByEmail(email)) {
            throw ApiException.conflict("Email already registered");
        }
        User user = User.builder()
                .name(req.name().trim())
                .email(email)
                .password(encoder.encode(req.password()))
                .role(Role.MEMBER)
                .build();
        return UserResponse.from(repo.save(user));
    }

    public AuthResponse login(LoginRequest req) {
        User user = repo.findByEmail(req.email().trim().toLowerCase())
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));
        if (!encoder.matches(req.password(), user.getPassword())) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
        return new AuthResponse(jwt.generate(user), user.getRole().name(), user.getId(), user.getName());
    }
}
