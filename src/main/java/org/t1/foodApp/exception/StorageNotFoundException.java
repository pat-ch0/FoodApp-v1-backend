package org.t1.foodApp.exception;

import org.springframework.web.server.ResponseStatusException;

public class StorageNotFoundException extends ResponseStatusException {

    public StorageNotFoundException(String message) {
        super(org.springframework.http.HttpStatus.NOT_FOUND, message);
    }
}
