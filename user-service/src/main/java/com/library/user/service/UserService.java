package com.library.user.service;

import com.library.user.common.ApiException;
import com.library.user.common.UserContext;
import com.library.user.dto.UserResponse;
import com.library.user.entity.Role;
import com.library.user.entity.User;
import com.library.user.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public UserResponse me() {
        Long id = UserContext.userId();
        if (id == null) {
            throw ApiException.badRequest("Missing X-User-Id header (call through the gateway)");
        }
        return get(id);
    }

    public List<UserResponse> list() {
        return repo.findAll().stream().map(UserResponse::from).toList();
    }

    public UserResponse get(Long id) {
        return UserResponse.from(find(id));
    }

    /** BR-12: الأدمن مينفعش يغير role نفسه. */
    public UserResponse changeRole(Long id, Role role) {
        if (id.equals(UserContext.userId())) {
            throw ApiException.forbidden("You cannot change your own role");
        }
        User user = find(id);
        user.setRole(role);
        return UserResponse.from(repo.save(user));
    }

    /** BR-12: الأدمن مينفعش يمسح نفسه. */
    public void delete(Long id) {
        if (id.equals(UserContext.userId())) {
            throw ApiException.forbidden("You cannot delete yourself");
        }
        repo.delete(find(id));
    }

    private User find(Long id) {
        return repo.findById(id).orElseThrow(() -> ApiException.notFound("User " + id + " not found"));
    }
}
