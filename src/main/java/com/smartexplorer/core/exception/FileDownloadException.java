package com.smartexplorer.core.exception;

/**
 * @Author
 * Karol Meksuła
 * 15-06-2018
 * */

public class FileDownloadException extends RuntimeException {
    private static String message;

    public FileDownloadException() {}

    public FileDownloadException(String message) {
        FileDownloadException.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
