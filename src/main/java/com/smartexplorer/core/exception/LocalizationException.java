package com.smartexplorer.core.exception;

/**
 * @Author
 * Karol Meksuła
 * 17-06-2018
 * */

public class LocalizationException extends RuntimeException {
    private static String message;

    public LocalizationException() {}

    public LocalizationException(String message) {
        LocalizationException.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
