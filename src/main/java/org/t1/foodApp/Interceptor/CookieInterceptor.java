package org.t1.foodApp.Interceptor;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.Arrays;
import java.util.UUID;
@Component
public class CookieInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getCookies() == null || Arrays.stream(request.getCookies()).noneMatch(cookie -> "user_cookie".equals(cookie.getName()))) {
            String uniqueID = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("user_cookie", uniqueID);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(24 * 60 * 60); // Expire en 1 jour
            response.addCookie(cookie);
        }
        return true;
    }
}
