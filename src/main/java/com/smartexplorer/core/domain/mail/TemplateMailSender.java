package com.smartexplorer.core.domain.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @Author
 * Karol Meksuła
 * 11-06-2018
 * */

@Service
public class TemplateMailSender implements MailSender {
    private Object attachment;

    private JavaMailSender javaMailSender;
    private TemplateEngine templateEngine;

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Autowired
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendMail(final MailType mailType, final MailReceiver mailReceiver) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");

        String htmlContent = templateEngine.process(mailType.getTemplate(), mailType.setContext(attachment));

        try {
            messageHelper.setSubject("Account confirmation");
            messageHelper.setTo(mailReceiver.getEmail());
            messageHelper.setText(htmlContent, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(mimeMessage);
    }

    @Override
    public void setAttachment(Object object) {
        this.attachment = object;
    }
}
