package org.t1.foodApp.exception;

public class CookieNotFoundException extends RuntimeException {

    public CookieNotFoundException(String cookieName) {
        super("Cookie '" + cookieName + "' non trouvé dans la requête.");
    }
}
