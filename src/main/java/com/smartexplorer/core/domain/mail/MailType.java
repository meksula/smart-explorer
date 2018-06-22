package com.smartexplorer.core.domain.mail;

import com.smartexplorer.core.domain.subject.registration.Confirmation;
import com.smartexplorer.core.domain.subject.spot.Spot;
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

        String template = "register_confirm.html";
    },

    SPOT_CREATION_CONFIRM {
        @Override
        public Context setContext(Object attachment) {
            Spot spot = (Spot) attachment;

            Context context = new Context();
            context.setVariable("latitude", spot.getLatitude());
            context.setVariable("longitude", spot.getLongitude());
            context.setVariable("buildingNumber", spot.getBuildingNumber());
            context.setVariable("street", spot.getStreet());
            context.setVariable("city", spot.getCity());
            context.setVariable("district", spot.getDistrict());
            context.setVariable("zipCode", spot.getZipCode());
            context.setVariable("country", spot.getCountry());

            return context;
        }

        @Override
        public String getTemplate() {
            return "spot_creation_confirm.html";
        }
    };

    public abstract Context setContext(Object attachment);

    public abstract String getTemplate();
}
