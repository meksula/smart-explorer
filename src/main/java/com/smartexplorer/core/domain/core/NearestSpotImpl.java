package com.smartexplorer.core.domain.core;

import com.google.maps.model.AddressComponent;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.smartexplorer.core.domain.subject.spot.Spot;
import com.smartexplorer.core.exception.SpotLocalizeException;
import com.smartexplorer.core.repository.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author
 * Karol Meksuła
 * 20-06-2018
 */

@Service
public class NearestSpotImpl implements NearestSpot {
    private Map<String, String> searchData;
    private SpotRepository spotRepository;
    private Haversine haversine;
    private GeocodingResult geocodingResult;
    private Spot spot;

    private final String LOCALITY = "locality";
    private final String DISTRICT = "administrative_area_level_2";

    public NearestSpotImpl() {
        this.haversine = new HaversineFormula();
    }

    @Autowired
    public void setSpotRepository(SpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }

    @Override
    public Spot findNearestSpot(GeocodingResult geocodingResult) {
        this.geocodingResult = geocodingResult;
        mapInit(geocodingResult);

        this.spot = searchSpotInCity(searchData.get(LOCALITY)).orElse(null);

        if (spot == null)
            this.spot = searchSpotInDisctrict(searchData.get(DISTRICT)).orElse(null);

        if (spot == null)
            throw new SpotLocalizeException("There is not Spots in your area.");

        return spot;
    }

    private void mapInit(GeocodingResult geocodingResult) {
        searchData = new LinkedHashMap<>();

        for (AddressComponent cmp : geocodingResult.addressComponents) {
            searchData.put(cmp.types[0].toCanonicalLiteral(), cmp.longName);
        }

    }

    public Map<String, String> getSearchData() {
        return searchData;
    }

    private Optional<Spot> searchSpotInCity(String city) {
        List<Spot> spotList = spotRepository.findByCity(city);

        if (spotList == null)
            return Optional.empty();

        if (spotList.size() == 1)
            return Optional.ofNullable(spotList.get(0));

        Map<String, Double> idDistance = computeDistance(spotList);

        String finalSpotId = findSmallestDistanceElement(idDistance);
        return spotList.stream().filter(item -> item.getId().equals(finalSpotId)).findAny();
    }

    private Optional<Spot> searchSpotInDisctrict(String district) {
        List<Spot> spotList = spotRepository.findByDistrict(district);

        if (spotList == null)
            return Optional.empty();

        if (spotList.size() == 1)
            return Optional.ofNullable(spotList.get(0));

        Map<String, Double> idDistance = computeDistance(spotList);

        String spotId = findSmallestDistanceElement(idDistance);
        return spotList.stream().filter(item -> item.getId().equals(spotId)).findAny();
    }

    private String findSmallestDistanceElement(Map<String, Double> map) {
        String spotId = "";
        double smallestDistance = 1000;

        for (Map.Entry<String, Double> entry : map.entrySet()) {
            if (entry.getValue() < smallestDistance) {
                spotId = entry.getKey();
                smallestDistance = entry.getValue();
            }
        }

        return spotId;
    }

    private Map<String, Double> computeDistance(List<Spot> spotList) {
        Map<String, Double> idDistance = new LinkedHashMap<>();

        spotList.forEach(spot -> {
            double distance = haversine.distance(geocodingResult.geometry.location, new LatLng(spot.getLatitude(), spot.getLongitude()));
            idDistance.put(spot.getId(), distance);
        });

        return idDistance;
    }

}
