package com.smartexplorer.core.domain.subject.spot;

import com.google.maps.model.GeocodingResult;
import com.smartexplorer.core.exception.SpotCreationException;

/**
 * @Author
 * Karol Meksuła
 * 14-06-2018
 * */

public abstract class SpotCreator {

    public final Spot createSpot(SpotCreationForm spotCreationForm) {
        boolean decission = validateForm(spotCreationForm);

        if (!decission)
            throw new SpotCreationException("Form is not valid.");

        GeocodingResult[] geocodigResults = geolocation(spotCreationForm);

        try {
            Spot spot = buildSpot(spotCreationForm, geocodigResults);
            saveSpot(spot);
            createStatsEntity(spot.getId());
            sendEmail(spot);
            return spot;
        } catch (RuntimeException re) {
            throw new SpotCreationException("Some values are 'null' or invalid.");
        }

    }

    protected abstract boolean validateForm(SpotCreationForm spotCreationForm);

    protected abstract GeocodingResult[] geolocation(SpotCreationForm spotCreationForm);

    protected abstract Spot buildSpot(SpotCreationForm spotCreationForm, GeocodingResult[] geocodigResults);

    protected abstract void saveSpot(Spot spot);

    protected abstract void createStatsEntity(String spotId);

    protected abstract void sendEmail(Spot spot);

}
