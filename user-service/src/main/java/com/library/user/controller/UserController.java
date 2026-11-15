package com.library.user.controller;

import com.library.user.dto.UpdateRoleRequest;
import com.library.user.dto.UserResponse;
import com.library.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User management (roles enforced by the API Gateway)")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(summary = "Get my profile (any logged-in user)")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "401", description = "Missing or invalid token")
    @GetMapping("/me")
    public UserResponse me() {
        return service.me();
    }

    @Operation(summary = "List all users (ADMIN, LIBRARIAN)")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "403", description = "Role not allowed")
    @GetMapping
    public List<UserResponse> list() {
        return service.list();
    }

    @Operation(summary = "Get a user by id (ADMIN, LIBRARIAN)")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/{id}")
    public UserResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @Operation(summary = "Change a user's role (ADMIN only)",
            description = "Example body: {\"role\":\"LIBRARIAN\"}. An admin cannot change their own role.")
    @ApiResponse(responseCode = "200", description = "Role updated")
    @ApiResponse(responseCode = "400", description = "Invalid role value")
    @ApiResponse(responseCode = "403", description = "Not ADMIN, or changing own role")
    @ApiResponse(responseCode = "404", description = "User not found")
    @PatchMapping("/{id}/role")
    public UserResponse changeRole(@PathVariable Long id, @Valid @RequestBody UpdateRoleRequest request) {
        return service.changeRole(id, request.role());
    }

    @Operation(summary = "Delete a user (ADMIN only)")
    @ApiResponse(responseCode = "204", description = "Deleted")
    @ApiResponse(responseCode = "403", description = "Not ADMIN, or deleting yourself")
    @ApiResponse(responseCode = "404", description = "User not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
