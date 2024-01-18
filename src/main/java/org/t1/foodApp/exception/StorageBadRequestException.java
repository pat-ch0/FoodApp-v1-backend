package org.t1.foodApp.exception;

public class StorageBadRequestException extends RuntimeException {

    public StorageBadRequestException(String message) {
        super(message);
    }
}
