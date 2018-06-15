package com.smartexplorer.core.domain.geolocation;

import com.google.maps.model.GeocodingResult;

/**
 * @Author
 * Karol Meksuła
 * 15-06-2018
 * */

public interface Geolocation {
    GeocodingResult[] geolocateByAddress(BasicAddress basicAddress);
}
