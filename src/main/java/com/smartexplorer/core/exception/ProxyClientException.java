package com.smartexplorer.core.exception;

/**
 * @Author
 * Karol Meksuła
 * 21-06-2018
 * */

public class ProxyClientException extends RuntimeException {
    private static String message;

    public ProxyClientException() {}

    public ProxyClientException(String message) {
        ProxyClientException.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
