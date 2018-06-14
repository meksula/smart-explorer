package com.smartexplorer.core.exception;

/**
 * @Author
 * Karol Meksuła
 * 14-06-2018
 * */

public class SpotCreationException extends RuntimeException {
    private static String message;

    public SpotCreationException() {}

    public SpotCreationException(String message) {
        SpotCreationException.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
