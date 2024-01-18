package org.t1.foodApp.exception;

public class StorageNotFoundException extends RuntimeException {

    public StorageNotFoundException(String message) {
        super(message);
    }
}
