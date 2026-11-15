package com.library.user.config;

import com.library.user.entity.Role;
import com.library.user.entity.User;
import com.library.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/** بيعمل حسابات تجريبية أول مرة السيرفس يشتغل (عشان الفريق يقدر يجرب فورًا). */
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedUsers(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            seed(repo, encoder, "Admin", "admin@library.com", "Admin@123", Role.ADMIN);
            seed(repo, encoder, "Librarian", "librarian@library.com", "Librarian@123", Role.LIBRARIAN);
            seed(repo, encoder, "Member", "member@library.com", "Member@123", Role.MEMBER);
        };
    }

    private void seed(UserRepository repo, PasswordEncoder encoder, String name, String email, String rawPassword, Role role) {
        if (!repo.existsByEmail(email)) {
            repo.save(User.builder().name(name).email(email)
                    .password(encoder.encode(rawPassword)).role(role).build());
        }
    }
}
