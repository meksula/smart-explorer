package com.smartexplorer.core.domain.domain.subject.spotmaker

import com.smartexplorer.core.SmarteplorerSpotmakerApplication
import com.smartexplorer.core.domain.subject.registration.RegistrationConfirmation
import com.smartexplorer.core.domain.subject.spotmaker.SpotMakerCreatorImpl
import com.smartexplorer.core.domain.subject.spotmaker.SpotMakerForm
import com.smartexplorer.core.repository.ConfirmationRepository
import com.smartexplorer.core.repository.SpotMakerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Shared
import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 10-06-2018
 * */

@SpringBootTest(classes = SmarteplorerSpotmakerApplication)
class SpotMarkerCreatorImplTest extends Specification {

    @Shared private SpotMakerCreatorImpl spotMarkerCreator

    @Autowired
    private SpotMakerRepository repository

    @Autowired
    private ConfirmationRepository confirmationRepository

    @Autowired
    private RegistrationConfirmation registrationConfirmation

    private SpotMakerForm spotMakerForm

    private static String username = "Clerifly"
    private static String password = "445rfcgv"
    private static String name = "Cler"
    private static String surname = "Abrevee"
    private static String email = "karol.meksula@onet.pl"
    private static int age = 34

    def 'spotMakerCreator should create new SpotMaker and save to database'() {
        setup:
        spotMarkerCreator = new SpotMakerCreatorImpl(repository)
        spotMarkerCreator.setPasswordEncoder(new BCryptPasswordEncoder())

        spotMarkerCreator.setRegistrationConfirmation(registrationConfirmation)


        when:
        this.spotMakerForm = new SpotMakerForm(username, password, name, surname, email, age)

        def spotMaker = spotMarkerCreator.createSpotMaker(spotMakerForm)

        then:
        println("spotMakerId " + spotMaker.spotMakerId)
        spotMaker.getSpotMakerId() != null
        username == spotMaker.getUsername()
        new BCryptPasswordEncoder().matches(password, spotMaker.getPassword())
        name == spotMaker.getName()
        surname == spotMaker.getSurname()
        email == spotMaker.getEmail()
        age == spotMaker.getAge()

        confirmationRepository.findAll().size() == 1

        cleanup:
        repository.deleteAll()
        confirmationRepository.deleteAll()
    }

}