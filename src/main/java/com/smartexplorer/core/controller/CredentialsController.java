package com.smartexplorer.core.controller;

import com.smartexplorer.core.domain.subject.registration.Confirmation;
import com.smartexplorer.core.domain.subject.registration.RegistrationConfirmation;
import com.smartexplorer.core.domain.subject.spotmaker.SpotMaker;
import com.smartexplorer.core.domain.subject.spotmaker.SpotMakerCreator;
import com.smartexplorer.core.domain.subject.spotmaker.SpotMakerForm;
import com.smartexplorer.core.exception.UserCreationException;
import com.smartexplorer.core.repository.SpotMakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

/**
 * @Author
 * Karol Meksuła
 * 13-06-2018
 * */

@RestController
@RequestMapping("/api/v1/")
public class CredentialsController {
    private SpotMakerCreator spotMakerCreator;
    private RegistrationConfirmation registrationConfirmation;
    private SpotMakerRepository spotMakerRepository;

    @Autowired
    public void setSpotMakerCreator(SpotMakerCreator spotMakerCreator) {
        this.spotMakerCreator = spotMakerCreator;
    }

    @Autowired
    public void setRegistrationConfirmation(RegistrationConfirmation registrationConfirmation) {
        this.registrationConfirmation = registrationConfirmation;
    }

    @Autowired
    public void setSpotMakerRepository(SpotMakerRepository spotMakerRepository) {
        this.spotMakerRepository = spotMakerRepository;
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    public SpotMaker registerSpotMaker(@RequestBody SpotMakerForm spotMakerForm) {
        return spotMakerCreator.createSpotMaker(spotMakerForm);
    }

    @PostMapping("/verification")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SpotMaker verifySpotMakerCreation(@RequestBody Confirmation confirmation) {
        return registrationConfirmation.confirmAndEnable(confirmation)
                .orElseThrow(() -> new UserCreationException("Cannot verify user. User not exist or verification code is invalid."));
    }

    @GetMapping("/verification/{spotMakerId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isSpotMakerVerificated(@PathVariable("spotMakerId") String spotMakerId) {
        return spotMakerRepository.findById(spotMakerId)
                .orElseThrow(() -> new UsernameNotFoundException(spotMakerId))
                .isConfirmed();
    }

}
