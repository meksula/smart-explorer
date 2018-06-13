package com.smartexplorer.core.domain.subject.registration;

import com.smartexplorer.core.domain.mail.MailSender;
import com.smartexplorer.core.domain.mail.MailType;
import com.smartexplorer.core.domain.subject.spotmaker.SpotMaker;
import com.smartexplorer.core.exception.UserCreationException;
import com.smartexplorer.core.repository.ConfirmationRepository;
import com.smartexplorer.core.repository.id.DefaultUniqueDatabaseIdCreator;
import com.smartexplorer.core.repository.SpotMakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author
 * Karol Meksuła
 * 11-06-2018
 * */

@Service
public class RegistrationConfirmationImpl implements RegistrationConfirmation {
    private ConfirmationRepository confirmationRepository;
    private SpotMakerRepository spotMakerRepository;
    private MailSender mailSender;

    @Autowired
    public void setConfirmationRepository(ConfirmationRepository confirmationRepository) {
        this.confirmationRepository = confirmationRepository;
    }

    @Autowired
    public void setSpotMakerRepository(SpotMakerRepository spotMakerRepository) {
        this.spotMakerRepository = spotMakerRepository;
    }

    @Autowired
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void confirmation(SpotMaker spotMaker) {
        String verificationCode = new DefaultUniqueDatabaseIdCreator().assignUniqueId();
        Confirmation confirmation = new Confirmation(spotMaker.getSpotMakerId(), verificationCode);
        confirmation.setId(verificationCode);

        confirmationRepository.save(confirmation);
        sendConfirmationMail(confirmation, spotMaker);
    }

    @Override
    public Optional<SpotMaker> confirmAndEnable(Confirmation confirmation) {
        Optional<Confirmation> confirmationOptional = confirmationRepository.findBySpotMakerId(confirmation.getSpotMakerId());
        Confirmation confirm = confirmationOptional
                .orElseThrow(() -> new UserCreationException("No user to verify."));

        if (confirm.getVerificationCode().equals(confirmation.getVerificationCode())) {
            confirmationRepository.deleteBySpotMakerId(confirmation.getSpotMakerId());
            Optional<SpotMaker> spotMakerOptional = spotMakerRepository.findById(confirmation.getSpotMakerId());
            spotMakerOptional.orElseThrow(() -> new UserCreationException("No such user: " + confirmation.getSpotMakerId()))
                    .setConfirmed(true);

            spotMakerRepository.save(spotMakerOptional.get());

            return spotMakerOptional;
        }

        else throw new UserCreationException("Cannot verify user - verification codes different!");
    }

    private void sendConfirmationMail(Confirmation confirmation, SpotMaker spotMaker) {
        mailSender.setAttachment(confirmation);
        mailSender.sendMail(MailType.REGISTER_CONFIRM, spotMaker);
    }

}
