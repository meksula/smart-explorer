package com.smartexplorer.core.domain.geolocation;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
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
        GeocodingResult[] geocodingResult = null;
        try {
            geocodingResult = request.await().clone();
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return geocodingResult;
    }

}
