package com.smartexplorer.core.domain.core;

import com.google.maps.model.GeocodingResult;

import java.util.Map;

/**
 * @Author
 * Karol Meksuła
 * 22-06-2018
 * */

public interface AddressComponentMapper {
    Map<String, String> mapAddressComponent(GeocodingResult geocodingResult);

    String mapLocality(GeocodingResult geocodingResult);

    String mapDistrict(GeocodingResult geocodingResult);

}
