package com.smartexplorer.core.domain.core;

import com.google.maps.model.LatLng;
import com.smartexplorer.core.domain.subject.explorers.Visit;
import com.smartexplorer.core.domain.subject.spot.Spot;

import java.util.List;
import java.util.Optional;

/**
 * @Author
 * Karol Meksuła
 * 17-06-2018
 * */

public interface SpotExploration {
    Optional<Spot> findNearestSpot(LatLng coordinates);

    Optional<Spot> findBySpotId(String spotId);

    List<Spot> findSpotsInCity(LatLng coordinates);

    List<Spot> findSpotsInDistrict(LatLng coordinates);

    List<Spot> findTopSpotsInCountry(int amount);

    Spot visitPlace(Visit visit);

    List<Visit> findVisitHistory(String explorerId);
}
