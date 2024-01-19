package org.t1.foodApp.exception;

import org.springframework.web.server.ResponseStatusException;

public class CookieNotFoundException extends ResponseStatusException {

    public CookieNotFoundException(String cookieName) {
        super(org.springframework.http.HttpStatus.NOT_FOUND, "Cookie " + cookieName + " not found");
    }
}
