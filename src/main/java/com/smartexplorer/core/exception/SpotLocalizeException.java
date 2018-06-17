package com.smartexplorer.core.exception;

/**
 * @Author
 * Karol Meksuła
 * 17-06-2018
 * */

public class SpotLocalizeException extends RuntimeException {
    private static String message;

    public SpotLocalizeException() {}

    public SpotLocalizeException(String message) {
        SpotLocalizeException.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
