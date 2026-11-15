package com.library.user.dto;

public record AuthResponse(String token, String role, Long userId, String name) {
}
