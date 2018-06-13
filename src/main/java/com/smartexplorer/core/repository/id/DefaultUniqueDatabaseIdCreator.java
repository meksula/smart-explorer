package com.smartexplorer.core.repository.id;

import java.util.Random;

/**
 * @Author
 * Karol Meksuła
 * 11-06-2018
 * */

public class DefaultUniqueDatabaseIdCreator implements UniqueDatabaseIdCreator {
    private final int ID_LENGTH = 25;

    private char[] lowcaseLetters = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o',
            'p', 'a', 's', 'd', 'f', 'g', 'h',
            'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm'};

    private int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    @Override
    public String assignUniqueId() {
        StringBuilder uniqueId = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < ID_LENGTH; i++) {
            if (random.nextBoolean())
                uniqueId.append(lowcaseLetters[random.nextInt(lowcaseLetters.length - 1)]);
            else uniqueId.append(numbers[random.nextInt(numbers.length - 1)]);
        }

        return uniqueId.toString();
    }

}
