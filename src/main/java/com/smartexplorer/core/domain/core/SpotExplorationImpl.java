package com.smartexplorer.core.domain.core;

import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.smartexplorer.core.domain.geolocation.Geolocation;
import com.smartexplorer.core.domain.subject.explorers.Visit;
import com.smartexplorer.core.domain.subject.spot.Spot;
import com.smartexplorer.core.domain.subject.spot.stats.StatisticsProvider;
import com.smartexplorer.core.exception.SpotLocalizeException;
import com.smartexplorer.core.repository.SpotRepository;
import com.smartexplorer.core.repository.VisitHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    private VisitHistoryRepository visitHistoryRepository;
    private NearestSpot nearestSpot;
    private AddressComponentMapper addressComponentMapper;

    public SpotExplorationImpl() {
        this.addressComponentMapper = new DefaultAddressComponentMapper();
    }

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

    @Autowired
    public void setVisitHistoryRepository(VisitHistoryRepository visitHistoryRepository) {
        this.visitHistoryRepository = visitHistoryRepository;
    }

    @Autowired
    public void setNearestSpot(NearestSpot nearestSpot) {
        this.nearestSpot = nearestSpot;
    }

    @Override
    public Optional<Spot> findNearestSpot(LatLng coordinates) {
        GeocodingResult geocodingResult = geolocation.geocodeCoordinates(coordinates)[0];
        return Optional.ofNullable(nearestSpot.findNearestSpot(geocodingResult));
    }

    @Override
    public Optional<Spot> findBySpotId(String spotId) {
        return spotRepository.findById(spotId);
    }

    @Override
    public List<Spot> findSpotsInCity(LatLng coordinates) {
        GeocodingResult geocodingResult = geolocation.geocodeCoordinates(coordinates)[0];

        return spotRepository.findByCity(addressComponentMapper.mapLocality(geocodingResult));
    }

    @Override
    public List<Spot> findSpotsInDistrict(LatLng coordinates) {
        GeocodingResult geocodingResult = geolocation.geocodeCoordinates(coordinates)[0];

        return spotRepository.findByDistrict(addressComponentMapper.mapDistrict(geocodingResult));
    }

    @Override
    public List<Spot> findTopSpotsInCountry(int amount) {
        return statisticsProvider.findMostVisited(amount);
    }

    @Override
    public Spot visitPlace(Visit visit) {
        visit.initDate();
        visitHistoryRepository.save(visit);

        return spotRepository.findById(visit.getSpotId())
                .orElseThrow(() -> new SpotLocalizeException("There is no spot with id: " + visit.getSpotId()));
    }

    @Override
    public List<Visit> findVisitHistory(String explorerId) {
        return visitHistoryRepository.findAllByExplorerId(explorerId);
    }

}
