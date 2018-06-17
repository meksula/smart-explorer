package com.smartexplorer.core.exception;

/**
 * @Author
 * Karol Meksuła
 * 15-06-2018
 * */

public class FileUploadException extends RuntimeException {
    private static String message;

    public FileUploadException() {}

    public FileUploadException(String message) {
        FileUploadException.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
