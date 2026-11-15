package com.library.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 1) بيمسح أي X-User-Id / X-User-Role جاية من الخارج (منع التزوير).
 * 2) لو فيه Bearer token صالح: بيعمل Authentication بالـ role،
 *    وبيبعت X-User-Id و X-User-Role للسيرفسز.
 * ملاحظة: مش @Component عن قصد، بيتعمل في SecurityConfig عشان يتسجل مرة واحدة.
 */
public class JwtAuthenticationFilter implements WebFilter {

    private static final String USER_ID = "X-User-Id";
    private static final String USER_ROLE = "X-User-Role";

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // نسخة جديدة قابلة للتعديل من الهيدرز، من غير X-User-* (هيدرز الطلب الأصلي read-only)
        HttpHeaders headers = new HttpHeaders();
        exchange.getRequest().getHeaders().forEach((name, values) -> {
            if (!USER_ID.equalsIgnoreCase(name) && !USER_ROLE.equalsIgnoreCase(name)) {
                headers.addAll(name, values);
            }
        });

        String auth = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (auth != null && auth.startsWith("Bearer ")) {
            try {
                Claims claims = jwtUtil.parse(auth.substring(7));
                String userId = claims.getSubject();
                String role = claims.get("role", String.class);
                if (userId != null && role != null) {
                    headers.set(USER_ID, userId);
                    headers.set(USER_ROLE, role);
                    var authentication = new UsernamePasswordAuthenticationToken(
                            userId, null, List.of(new SimpleGrantedAuthority("ROLE_" + role)));
                    return chain.filter(withHeaders(exchange, headers))
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
                }
            } catch (JwtException | IllegalArgumentException e) {
                // توكن غلط أو منتهي: نكمل من غير authentication، والـ Security هترجّع 401
            }
        }
        return chain.filter(withHeaders(exchange, headers));
    }

    private ServerWebExchange withHeaders(ServerWebExchange exchange, HttpHeaders headers) {
        ServerHttpRequest request = new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                return headers;
            }
        };
        return exchange.mutate().request(request).build();
    }
}