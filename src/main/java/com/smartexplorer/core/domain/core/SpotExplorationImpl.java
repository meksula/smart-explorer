package com.smartexplorer.core.domain.core;

import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.smartexplorer.core.domain.geolocation.Geolocation;
import com.smartexplorer.core.domain.subject.spot.Spot;
import com.smartexplorer.core.domain.subject.spot.stats.StatisticsProvider;
import com.smartexplorer.core.repository.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author
 * Karol Meksuła
 * 17-06-2018
 * */

@Service
public class SpotExplorationImpl implements SpotExploration {
    private SpotRepository spotRepository;
    private Geolocation geolocation;
    private StatisticsProvider statisticsProvider;

    @Autowired
    public void setSpotRepository(SpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }

    @Autowired
    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
    }

    @Autowired
    public void setStatisticsProvider(StatisticsProvider statisticsProvider) {
        this.statisticsProvider = statisticsProvider;
    }

    @Override
    public Optional<Spot> findNearestSpot(LatLng coordinates) {
        //TODO
        return Optional.empty();
    }

    @Override
    public Optional<Spot> findBySpotId(String spotId) {
        return spotRepository.findById(spotId);
    }

    @Override
    public List<Spot> findSpotsInCity(LatLng coordinates) {
        GeocodingResult geocodingResult = geolocation.geocodeCoordinates(coordinates)[0];
        String city;

        if (geocodingResult.addressComponents.length == 8)
            city = geocodingResult.addressComponents[4].longName;

        else if (geocodingResult.addressComponents.length == 7)
            city = geocodingResult.addressComponents[3].longName;

        else city = geocodingResult.addressComponents[1].longName;

        return spotRepository.findByCity(city);
    }

    @Override
    public List<Spot> findSpotsInDistrict(LatLng coordinates) {
        GeocodingResult geocodingResult = geolocation.geocodeCoordinates(coordinates)[0];
        String district;

        if (geocodingResult.addressComponents.length == 8)
            district = geocodingResult.addressComponents[3].longName;

        else if (geocodingResult.addressComponents.length == 7)
            district = geocodingResult.addressComponents[2].longName;

        else district = geocodingResult.addressComponents[2].longName;

        return spotRepository.findByDistrict(district);
    }

    @Override
    public List<Spot> findTopSpotsInCountry(int amount) {
        return statisticsProvider.findMostVisited(amount);
    }

}
