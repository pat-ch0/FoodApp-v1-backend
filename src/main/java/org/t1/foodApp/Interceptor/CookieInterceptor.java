package org.t1.foodApp.Interceptor;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.Arrays;
import java.util.UUID;
import org.springframework.http.ResponseCookie;
@Component
public class CookieInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getCookies() == null || Arrays.stream(request.getCookies()).noneMatch(cookie -> "user_cookie".equals(cookie.getName()))) {
            String uniqueID = UUID.randomUUID().toString();

            ResponseCookie cookie = ResponseCookie.from("user_cookie", uniqueID)
            .httpOnly(false)
            .maxAge(24 * 60 * 60)
            .sameSite("None")
            .secure(true)
            .build();
            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        }
        return true;
    }
}
