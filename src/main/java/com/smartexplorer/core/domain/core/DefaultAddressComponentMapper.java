package com.smartexplorer.core.domain.core;

import com.google.maps.model.GeocodingResult;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author
 * Karol Meksuła
 * 22-06-2018
 * */

public class DefaultAddressComponentMapper implements AddressComponentMapper {
    private Map<String, String> addressComponents;

    @Override
    public Map<String, String> mapAddressComponent(GeocodingResult geocodingResult) {
        return addressComponentsMapping(geocodingResult);
    }

    @Override
    public String mapLocality(GeocodingResult geocodingResult) {
        return addressComponentsMapping(geocodingResult).get("locality");
    }

    @Override
    public String mapDistrict(GeocodingResult geocodingResult) {
        return addressComponentsMapping(geocodingResult).get("administrative_area_level_2");
    }

    private Map<String, String> addressComponentsMapping(GeocodingResult geocodingResult) {
        this.addressComponents = new LinkedHashMap<>();

        for (int i = 0; i < geocodingResult.addressComponents.length; i++) {
            addressComponents.put(geocodingResult.addressComponents[i].types[0].toCanonicalLiteral(),
                    geocodingResult.addressComponents[i].longName);
        }

        return this.addressComponents;
    }

}
