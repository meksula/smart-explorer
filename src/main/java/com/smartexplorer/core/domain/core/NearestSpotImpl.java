package com.smartexplorer.core.domain.core;

import com.google.maps.model.GeocodingResult;
import com.smartexplorer.core.domain.subject.spot.Spot;
import com.smartexplorer.core.exception.CannotDeserializeAddress;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author
 * Karol Meksuła
 * 20-06-2018
 * */

@Service
public class NearestSpotImpl implements NearestSpot {
    private Map<String, String> searchData;

    @Override
    public Spot findNearestSpot(GeocodingResult geocodingResult) {
        mapInit(geocodingResult);
        return null;
    }

    private void mapInit(GeocodingResult geocodingResult) {
        searchData = new LinkedHashMap<>();

        int length = geocodingResult.addressComponents.length;

        if (length <= 6)
            this.searchData = AddressDeserializer.VILLAGE.deserialize(geocodingResult);
        else if (length == 7)
            this.searchData = AddressDeserializer.TOWN.deserialize(geocodingResult);
        else if (length == 8)
            this.searchData = AddressDeserializer.CITY.deserialize(geocodingResult);

        else throw new CannotDeserializeAddress("Cannot deserialize address.");
    }

    public Map<String, String> getSearchData() {
        return searchData;
    }
}
