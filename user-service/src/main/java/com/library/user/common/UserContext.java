package com.library.user.common;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * الـ Gateway بيتحقق من الـ JWT وبيبعت الهيدرز دي لكل سيرفس:
 *   X-User-Id   X-User-Role
 * استخدم: UserContext.userId() و UserContext.role()
 */
public final class UserContext {
    private UserContext() {}

    public static Long userId() {
        String v = header("X-User-Id");
        return v == null ? null : Long.valueOf(v);
    }

    public static String role() { return header("X-User-Role"); }

    private static String header(String name) {
        var attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs == null ? null : attrs.getRequest().getHeader(name);
    }
}
