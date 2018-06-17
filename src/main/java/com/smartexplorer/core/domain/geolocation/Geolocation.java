package com.smartexplorer.core.domain.geolocation;

import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

/**
 * @Author
 * Karol Meksuła
 * 15-06-2018
 * */

public interface Geolocation {
    GeocodingResult[] geolocateByAddress(BasicAddress basicAddress);

    GeocodingResult[] geocodeCoordinates(LatLng coordinates);
}
