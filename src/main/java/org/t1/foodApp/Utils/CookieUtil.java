package org.t1.foodApp.Utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.t1.foodApp.exception.CookieNotFoundException;

public class CookieUtil {



    public static Cookie createWithHttpOnly(HttpServletResponse httpServletResponse, String cookieName, String token) {
        Cookie cookie = new Cookie(cookieName, token);
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(-1);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        return cookie;
    }

    public static String getCookieValue(HttpServletRequest request, String cookieName) throws CookieNotFoundException {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        throw new CookieNotFoundException(cookieName);
    }
}
