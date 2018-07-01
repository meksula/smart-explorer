package com.smartexplorer.core.domain.mail

import com.smartexplorer.core.SmarteplorerSpotmakerApplication
import com.smartexplorer.core.domain.subject.registration.Confirmation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mail.javamail.JavaMailSender
import org.thymeleaf.TemplateEngine
import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 11-06-2018
 * */

@SpringBootTest(classes = SmarteplorerSpotmakerApplication)
class TemplateMailSenderTest extends Specification {

    @Autowired
    private JavaMailSender javaMailSender

    @Autowired
    private TemplateEngine templateEngine

    def 'email sending integration test'() {
        setup:
        def sut = new TemplateMailSender(javaMailSender: javaMailSender, templateEngine: templateEngine)

        Confirmation confirmation = new Confirmation("principalNumber", "verificationCode")
        sut.setAttachment(confirmation)

        sut.sendMail(MailType.REGISTER_CONFIRM, new MailReceiver() {
            @Override
            String getEmail() {
                return "karol.meksula@onet.pl"
            }
        })

    }

}
