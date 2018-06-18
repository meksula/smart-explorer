package com.smartexplorer.core.exception;

/**
 * @Author
 * Karol Meksuła
 * 18-06-2018
 * */

public class StatisticsCounterException extends RuntimeException {
    private static String message;

    public StatisticsCounterException() {}

    public StatisticsCounterException(String message) {
        StatisticsCounterException.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
