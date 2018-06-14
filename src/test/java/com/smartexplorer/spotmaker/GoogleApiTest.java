package com.smartexplorer.spotmaker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

/**
 * @Author
 * Karol Meksuła
 * 08-06-2018
 *
 * Learning class, created in order to learn how Google maps api works.
 * */

@Slf4j
public class GoogleApiTest {
    private GeoApiContext geoApiContext = new GeoApiContext
            .Builder()
            .apiKey("AIzaSyCiyZT2HXKcIAzIPodam8cXYLkb_cTiKQ4")
            .build();

    /**
     * How to localize by google API key
     * Notice that GeolocationApi is implemented as Builder pattern and you can specify more parameters.
     * It is can improve an accurancy.
     * */

    private GeolocationResult localiseMe() throws InterruptedException, ApiException, IOException {
        return GeolocationApi.newRequest(geoApiContext).CreatePayload().await();
    }

    @Test
    public void geolocationApiRequest() throws InterruptedException, ApiException, IOException {
        GeolocationResult result = localiseMe();

        log.info("Accurancy: " + result.accuracy);
        log.info("Longitude: " + result.location.lng);
        log.info("Latitude: " + result.location.lat);
    }

    /**
     * How to convert GeolocationResult to real address
     * */

    @Test
    public void coordinatesGeocodigTest() throws InterruptedException, ApiException, IOException {
        GeolocationResult result = localiseMe();

        LatLng latLng = result.location;

        GeocodingApiRequest request = GeocodingApi.reverseGeocode(geoApiContext, latLng);
        GeocodingResult[] geocodigResults = request.await().clone();

        for (GeocodingResult g : geocodigResults) {
            log.info(g.formattedAddress);
        }

    }

    /**
     * How to convert real address to coordinates
     * */

    @Test
    public void addressGeocodingTest() throws InterruptedException, ApiException, IOException {
        GeocodingApiRequest request = GeocodingApi.geocode(geoApiContext, "Warszawa, ul. Marszałkowska 5");
        GeocodingResult[] geocodingResult = request.await().clone();

        for (GeocodingResult g : geocodingResult) {
            log.info("Formatted address: " + g.formattedAddress);
            log.info("Place ID: " + g.placeId);
            log.info(String.valueOf("Latitude: " + g.geometry.location.lat));
            log.info(String.valueOf("Longitude: " + g.geometry.location.lng));
            log.info("Location Type: " + g.geometry.locationType.toUrlValue());

            for (AddressComponent a : g.addressComponents)
                log.info("Address component: " + a.longName);
        }
    }

    /**
     * How to assign distance between 2 points
     * */

    @Test
    public void assignDistanceBetweenTwoPoints() throws InterruptedException, ApiException, IOException {

        /*
        * Unfortunatelly I don't know how do this by latidute and longitude.
        * Have to convert to address.
        *
        * For example:
        * User's location posted convert to address and then put into DistanceMatrixApi
        * */

        DistanceMatrix distanceMatrix = DistanceMatrixApi
                .getDistanceMatrix(geoApiContext, new String[]{"Lubartów", "ul. Łąkowa"},
                        new String[]{"Lublin", "ul. Unicka"})
                .mode(TravelMode.WALKING)
                .await();

        ObjectMapper objectMapper = new ObjectMapper();
        log.info(objectMapper.writeValueAsString(distanceMatrix));
    }

}
