package com.library.user.controller;

import com.library.user.dto.UserResponse;
import com.library.user.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** للتواصل بين السيرفسز (Feign) بس. الـ Gateway مش بيعمله route. */
@Hidden
@RestController
@RequestMapping("/internal/users")
public class InternalUserController {

    private final UserService service;

    public InternalUserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable Long id) {
        return service.get(id);
    }
}
