package com.smartexplorer.core.domain.core;

import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author
 * Karol Meksuła
 * 20-06-2018
 * */

public enum AddressDeserializer {
    VILLAGE {
        @Override
        public Map<String, String> deserialize(GeocodingResult geocodingResult) {
            Map<String, String> deserialized = new LinkedHashMap<>();
            AddressComponent[] components = geocodingResult.addressComponents;

            deserialized.put("buildingNumber", components[0].longName);
            deserialized.put("city", components[1].longName);
            deserialized.put("district", components[2].longName);
            deserialized.put("voivodeship", components[3].longName);
            deserialized.put("country", components[4].longName);
            deserialized.put("zipCode", components[5].longName);

            return deserialized;
        }
    }, TOWN {
        @Override
        public Map<String, String> deserialize(GeocodingResult geocodingResult) {
            Map<String, String> deserialized = new LinkedHashMap<>();
            AddressComponent[] components = geocodingResult.addressComponents;

            deserialized.put("buildingNumber", components[0].longName);
            deserialized.put("street", components[1].longName);
            deserialized.put("district", components[2].longName);
            deserialized.put("city", components[3].longName);
            deserialized.put("voivodeship", components[4].longName);
            deserialized.put("country", components[5].longName);
            deserialized.put("zipCode", components[6].longName);

            return deserialized;
        }
    }, CITY {
        @Override
        public Map<String, String> deserialize(GeocodingResult geocodingResult) {
            Map<String, String> deserialized = new LinkedHashMap<>();
            AddressComponent[] components = geocodingResult.addressComponents;

            deserialized.put("buildingNumber", components[0].longName);
            deserialized.put("street", components[1].longName);
            deserialized.put("quarter", components[2].longName);
            deserialized.put("district", components[3].longName);
            deserialized.put("city", components[4].longName);
            deserialized.put("voivodeship", components[5].longName);
            deserialized.put("country", components[6].longName);
            deserialized.put("zipCode", components[7].longName);

            return deserialized;
        }
    };

    public abstract Map<String, String> deserialize(GeocodingResult geocodingResult) throws ArrayIndexOutOfBoundsException;
}
