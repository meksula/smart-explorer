package com.smartexplorer.core.domain.subject.spotmaker;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author
 * Karol Meksuła
 * 11-06-2018
 * */

@Component
public class SpotMakerValidatorDefault implements SpotMakerValidator {
    private SpotMakerForm spotMakerForm;
    private boolean[] decissions;

    private final String USERNAME_PATTERN = "[0-9a-zA-Z]{6,25}";
    private final String NAME_PATTERN = "[a-zA-Złóżźćłńęą]{2,25}";
    private final String EMAIL_PATTERN = ".+@{1}.+\\.[a-z]{2,}";

    @Override
    public boolean validateSpotMaker(SpotMakerForm spotMakerForm) {
        this.spotMakerForm = spotMakerForm;

        decissions = new boolean[6];
        decissions[0] = checkUsername();
        decissions[1] = checkPassword();
        decissions[2] = checkName();
        decissions[3] = checkSurname();
        decissions[4] = checkEmail();
        decissions[5] = checkAge();

        return summary();
    }

    private boolean checkUsername() {
       return checkValue(spotMakerForm.getUsername(), USERNAME_PATTERN);
    }

    private boolean checkPassword() {
        return checkValue(spotMakerForm.getPassword(), USERNAME_PATTERN);
    }

    private boolean checkName() {
        return checkValue(spotMakerForm.getName(), NAME_PATTERN);
    }

    private boolean checkSurname() {
        return checkValue(spotMakerForm.getSurname(), NAME_PATTERN);
    }

    private boolean checkEmail() {
        return checkValue(spotMakerForm.getEmail(), EMAIL_PATTERN);
    }

    private boolean checkAge() {
        return spotMakerForm.getAge() > 18 && spotMakerForm.getAge() < 99;
    }

    private boolean checkValue(final String toCheck, final String pattern) {
        Pattern compilePattern = Pattern.compile(pattern);
        Matcher matcher = compilePattern.matcher(toCheck);
        return matcher.matches();
    }

    private boolean summary() {
        for (boolean b : decissions) {
            if (!b)
                return false;
        }
        return true;
    }

}
