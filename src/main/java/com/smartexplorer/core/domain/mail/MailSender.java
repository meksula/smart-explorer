package com.smartexplorer.core.domain.mail;

/**
 * @Author
 * Karol Meksuła
 * 11-06-2018
 * */

public interface MailSender {
    void sendMail(final MailType mailType, final MailReceiver mailReceiver);

    void setAttachment(Object object);
}
