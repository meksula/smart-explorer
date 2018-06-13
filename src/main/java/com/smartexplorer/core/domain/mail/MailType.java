package com.smartexplorer.core.domain.mail;

import com.smartexplorer.core.domain.subject.registration.Confirmation;
import org.thymeleaf.context.Context;

/**
 * @Author
 * Karol Meksuła
 * 11-06-2018
 * */

public enum MailType {

    SIMPLE_MAIL {
        @Override
        public Context setContext(Object attachment) {
            Context context = new Context();

            return context;
        }

        @Override
        public String getTemplate() {
            return null;
        }
    },

    REGISTER_CONFIRM {
        @Override
        public Context setContext(Object attachment) {
            Confirmation confirmation = (Confirmation) attachment;

            Context context = new Context();
            context.setVariable("spotMakerId", confirmation.getSpotMakerId());
            context.setVariable("verificationCode", confirmation.getVerificationCode());

            return context;
        }

        @Override
        public String getTemplate() {
            return template;
        }

        String template = "/mail/register_confirm.html";
    };

    public abstract Context setContext(Object attachment);

    public abstract String getTemplate();
}
