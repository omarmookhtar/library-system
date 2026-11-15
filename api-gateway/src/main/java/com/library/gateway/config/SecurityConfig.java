package com.library.gateway.config;

import com.library.gateway.security.ErrorWriter;
import com.library.gateway.security.JwtAuthenticationFilter;
import com.library.gateway.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

/**
 * مصفوفة الصلاحيات (مكان واحد لكل المشروع).
 * أول قاعدة بتطابق هي اللي بتتطبق، فالترتيب مهم (الأخص قبل الأعم).
 * أي endpoint مش مكتوب هنا: لازم المستخدم يكون مسجل دخول بس (أي role)،
 * وعمر بيضيفله القاعدة الصح.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, JwtUtil jwtUtil) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .addFilterAt(new JwtAuthenticationFilter(jwtUtil), SecurityWebFiltersOrder.AUTHENTICATION)
                .exceptionHandling(e -> e
                        .authenticationEntryPoint((exchange, ex) ->
                                ErrorWriter.write(exchange, HttpStatus.UNAUTHORIZED, "Authentication required or token invalid"))
                        .accessDeniedHandler((exchange, ex) ->
                                ErrorWriter.write(exchange, HttpStatus.FORBIDDEN, "You do not have permission to access this resource")))
                .authorizeExchange(auth -> auth
                        // CORS preflight
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()

                        // ---------- Auth (public) ----------
                        .pathMatchers(HttpMethod.POST, "/api/auth/register", "/api/auth/login").permitAll()

                        // ---------- Users ----------
                        .pathMatchers(HttpMethod.GET, "/api/users/me").authenticated()
                        .pathMatchers(HttpMethod.GET, "/api/users", "/api/users/*").hasAnyRole("ADMIN", "LIBRARIAN")
                        .pathMatchers(HttpMethod.PATCH, "/api/users/*/role").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.DELETE, "/api/users/*").hasRole("ADMIN")

                        // ---------- Books (catalog) ----------
                        .pathMatchers(HttpMethod.GET, "/api/books", "/api/books/*").authenticated()
                        .pathMatchers(HttpMethod.POST, "/api/books").hasAnyRole("ADMIN", "LIBRARIAN")
                        .pathMatchers(HttpMethod.PUT, "/api/books/*").hasAnyRole("ADMIN", "LIBRARIAN")
                        .pathMatchers(HttpMethod.DELETE, "/api/books/*").hasRole("ADMIN")

                        // ---------- Borrowings ----------
                        .pathMatchers(HttpMethod.POST, "/api/borrowings").hasAnyRole("MEMBER", "LIBRARIAN")
                        .pathMatchers(HttpMethod.POST, "/api/borrowings/*/return").hasAnyRole("LIBRARIAN", "ADMIN")
                        .pathMatchers(HttpMethod.PATCH, "/api/borrowings/*/renew").hasRole("MEMBER")
                        .pathMatchers(HttpMethod.GET, "/api/borrowings/my").hasRole("MEMBER")
                        .pathMatchers(HttpMethod.GET, "/api/borrowings", "/api/borrowings/*").hasAnyRole("ADMIN", "LIBRARIAN")

                        // ---------- Fines & Reports ----------
                        .pathMatchers(HttpMethod.GET, "/api/fines/my").hasRole("MEMBER")
                        .pathMatchers(HttpMethod.GET, "/api/fines").hasAnyRole("ADMIN", "LIBRARIAN")
                        .pathMatchers(HttpMethod.POST, "/api/fines/*/pay").hasAnyRole("LIBRARIAN", "ADMIN")
                        .pathMatchers(HttpMethod.GET, "/api/reports/overdue").hasAnyRole("ADMIN", "LIBRARIAN")
                        .pathMatchers(HttpMethod.GET, "/api/reports/popular-books", "/api/reports/summary").hasRole("ADMIN")

                        // ---------- أي حاجة تانية ----------
                        .pathMatchers("/internal/**").denyAll()
                        .anyExchange().authenticated())
                .build();
    }
}
