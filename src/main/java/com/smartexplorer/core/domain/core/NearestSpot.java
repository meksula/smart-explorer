package com.smartexplorer.core.domain.core;

import com.google.maps.model.GeocodingResult;
import com.smartexplorer.core.domain.subject.spot.Spot;

/**
 * @Author
 * Karol Meksuła
 * 20-06-2018
 * */

public interface NearestSpot {
    Spot findNearestSpot(GeocodingResult geocodingResult);
}
