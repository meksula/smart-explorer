package com.smartexplorer.core.domain.geolocation;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.smartexplorer.core.exception.LocalizationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author
 * Karol Meksuła
 * 15-06-2018
 * */

@Service
public class GoogleMapsGeolocation implements Geolocation {
    private GeoApiContext geoApiContext;

    @Value("${google.api.key}")
    private String apiKey;

    public GoogleMapsGeolocation() {
        this.geoApiContext = new GeoApiContext
                .Builder()
                .apiKey("AIzaSyCiyZT2HXKcIAzIPodam8cXYLkb_cTiKQ4")
                .build();
    }

    @Override
    public GeocodingResult[] geolocateByAddress(BasicAddress basicAddress) {
        GeocodingApiRequest request = GeocodingApi.geocode(geoApiContext, basicAddress.toString());
        try {
            return request.await().clone();
        } catch (ApiException | InterruptedException | IOException e) {
            e.getStackTrace();
            throw new LocalizationException("Something went wrong. Cannot localize: " + basicAddress.toString());
        }

    }

    @Override
    public GeocodingResult[] geocodeCoordinates(LatLng coordinates) {
        GeocodingApiRequest request = GeocodingApi.reverseGeocode(geoApiContext, coordinates);
        try {
            return request.await().clone();
        } catch (ApiException | InterruptedException | IOException e) {
            throw new LocalizationException("Something went wrong. Cannot geocode: " + coordinates.toString());
        }

    }

}
