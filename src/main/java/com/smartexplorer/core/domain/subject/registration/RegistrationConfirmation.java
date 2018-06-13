package com.smartexplorer.core.domain.subject.registration;

import com.smartexplorer.core.domain.subject.spotmaker.SpotMaker;

import java.util.Optional;

/**
 * @Author
 * Karol Meksuła
 * 11-06-2018
 * */

public interface RegistrationConfirmation {
    void confirmation(SpotMaker spotMaker);

    Optional<SpotMaker> confirmAndEnable(Confirmation confirmation);
}
