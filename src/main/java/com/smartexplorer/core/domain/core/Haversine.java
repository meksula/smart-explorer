package com.smartexplorer.core.domain.core;

import com.google.maps.model.LatLng;

/**
 * @Author
 * Karol Meksuła
 * 21-06-2018
 * */

public interface Haversine {
    double distance(LatLng pointA, LatLng pointB);
}
