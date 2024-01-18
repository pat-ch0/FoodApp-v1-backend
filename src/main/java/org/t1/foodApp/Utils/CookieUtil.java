package org.t1.foodApp.Utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.t1.foodApp.exception.CookieNotFoundException;

public class CookieUtil {

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
