package org.t1.foodApp.exception;

import org.springframework.web.server.ResponseStatusException;

public class StorageBadRequestException extends ResponseStatusException {

    public StorageBadRequestException(String message) {
        super(org.springframework.http.HttpStatus.BAD_REQUEST, message);
    }
}
