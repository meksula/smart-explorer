package com.smartexplorer.core.domain.subject.spotmaker;

import com.smartexplorer.core.exception.UserCreationException;
import com.smartexplorer.core.domain.subject.registration.RegistrationConfirmation;
import com.smartexplorer.core.repository.id.DefaultUniqueDatabaseIdCreator;
import com.smartexplorer.core.repository.SpotMakerRepository;
import com.smartexplorer.core.repository.id.UniqueDatabaseIdCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author
 * Karol Meksuła
 * 10-06-2018
 * */

@Service
public class SpotMakerCreatorImpl implements SpotMakerCreator {
    private SpotMakerValidator spotMakerValidator;
    private SpotMakerRepository spotMakerRepository;
    private PasswordEncoder passwordEncoder;
    private UniqueDatabaseIdCreator uniqueDatabaseIdCreator;
    private RegistrationConfirmation registrationConfirmation;

    public SpotMakerCreatorImpl(SpotMakerRepository spotMakerRepository) {
        this.spotMakerValidator = new SpotMakerValidatorDefault();
        this.spotMakerRepository = spotMakerRepository;
        this.uniqueDatabaseIdCreator = new DefaultUniqueDatabaseIdCreator();
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setRegistrationConfirmation(RegistrationConfirmation registrationConfirmation) {
        this.registrationConfirmation = registrationConfirmation;
    }

    @Override
    public SpotMaker createSpotMaker(SpotMakerForm form) {
        SpotMaker spotMaker;
        if (spotMakerValidator.validateSpotMaker(form)) {
            spotMaker = buildSpotMaker(form);
            save(spotMaker);
            createConfirmationProcess(spotMaker);
        } else throw new UserCreationException("Some values in your form are incorrect.");

        return spotMaker;
    }

    private SpotMaker buildSpotMaker(SpotMakerForm spotMakerForm) {
        return new SpotMaker.SpotMakerBuilder()
                .spotMakerId(uniqueDatabaseIdCreator.assignUniqueId())
                .isConfirmed(false)
                .username(spotMakerForm.getUsername())
                .password(passwordEncoder.encode(spotMakerForm.getPassword()))
                .name(spotMakerForm.getName())
                .surname(spotMakerForm.getSurname())
                .email(spotMakerForm.getEmail())
                .age(spotMakerForm.getAge())
                .authorities(new String[]{"SPOT_MAKER"})
                .build();
    }

    private void save(SpotMaker spotMaker) {
        if (spotMakerRepository.findByUsername(spotMaker.getUsername()).isPresent())
            throw new UserCreationException("User [" + spotMaker.getUsername() + "] just exist in database.");

        if (spotMakerRepository.findByEmail(spotMaker.getEmail()).isPresent())
            throw new UserCreationException("User with e-mail: [" + spotMaker.getEmail() + "] just exist in database.");

        spotMakerRepository.save(spotMaker);
    }

    private void createConfirmationProcess(SpotMaker spotMaker) {
        registrationConfirmation.confirmation(spotMaker);
    }

}