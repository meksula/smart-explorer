package com.smartexplorer.core.exception;

/**
 * @Author
 * Karol Meksuła
 * 11-06-2018
 * */

public class UserCreationException extends RuntimeException {
    public static String message;

    public UserCreationException() {}

    public UserCreationException(final String message) {
        UserCreationException.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
