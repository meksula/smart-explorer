package com.smartexplorer.core.exception;

/**
 * @Author
 * Karol Meksuła
 * 20-06-2018
 * */

public class CannotDeserializeAddress extends RuntimeException {
    private static String message;

    public CannotDeserializeAddress() {}

    public CannotDeserializeAddress(String message) {
        CannotDeserializeAddress.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
