package com.smartexplorer.core.controller;

import com.google.maps.model.LatLng;
import com.smartexplorer.core.domain.core.SpotExploration;
import com.smartexplorer.core.domain.subject.explorers.Visit;
import com.smartexplorer.core.domain.subject.spot.Spot;
import com.smartexplorer.core.domain.subject.spot.information.SpotInformationManager;
import com.smartexplorer.core.domain.subject.spot.stats.SpotSearch;
import com.smartexplorer.core.exception.SpotLocalizeException;
import com.smartexplorer.core.repository.SpotStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author
 * Karol Meksuła
 * 17-06-2018
 * */

@RestController
@RequestMapping("/api/v1/spot/exploration")
public class SpotExplorationController {
    private SpotExploration spotExploration;
    private SpotStatisticsRepository spotStatisticsRepository;
    private SpotInformationManager spotInformationManager;

    @Autowired
    public void setSpotExploration(SpotExploration spotExploration) {
        this.spotExploration = spotExploration;
    }

    @Autowired
    public void setSpotStatisticsRepository(SpotStatisticsRepository spotStatisticsRepository) {
        this.spotStatisticsRepository = spotStatisticsRepository;
    }

    @Autowired
    public void setSpotInformationManager(SpotInformationManager spotInformationManager) {
        this.spotInformationManager = spotInformationManager;
    }

    @SpotSearch
    @PostMapping("/nearest")
    @ResponseStatus(HttpStatus.OK)
    public Spot getNearestSpot(@RequestBody LatLng coordinates) {
        return spotExploration.findNearestSpot(coordinates)
                .orElseThrow(() -> new SpotLocalizeException("Cannot localize any spot for your coordinates: "
                        + coordinates.toString()));
    }

    @SpotSearch
    @PostMapping("/city")
    @ResponseStatus(HttpStatus.OK)
    public List<Spot> getSpotListInCity(@RequestBody LatLng coordinates) {
        List<Spot> spotList = spotExploration.findSpotsInCity(coordinates);
        return checkIfEmpty(spotList, "There is no spots in your city.");
    }

    @SpotSearch
    @PostMapping("/district")
    @ResponseStatus(HttpStatus.OK)
    public List<Spot> getSpotListInDistrict(@RequestBody LatLng coordinates) {
        List<Spot> spotList = spotExploration.findSpotsInDistrict(coordinates);
        return checkIfEmpty(spotList, "There is no spots in your district.");
    }

    @GetMapping("/top/{amount}")
    @ResponseStatus(HttpStatus.OK)
    public List<Spot> getTopSpots(@PathVariable("amount") int amount) {
        List<Spot> spotList = spotExploration.findTopSpotsInCountry(amount);
        return checkIfEmpty(spotList, "Cannot load top list.");
    }

    private List<Spot> checkIfEmpty(List<Spot> spotList, String errorMessage) {
        if (spotList.isEmpty())
            throw new SpotLocalizeException(errorMessage);
        else return spotList;
    }

    @SpotSearch
    @GetMapping("/{spotId}")
    @ResponseStatus(HttpStatus.OK)
    public Spot getSpotById(@PathVariable("spotId") String spotId) {
        return spotExploration.findBySpotId(spotId)
                .orElseThrow(() -> new SpotLocalizeException("There is no spot with id: " + spotId));
    }

    @PreAuthorize("hasAuthority('PROXY')")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Spot markAsVisited(@RequestBody Visit visit) {
        return spotExploration.visitPlace(visit);
    }

    @GetMapping("/history/{explorerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Visit> getExplorersHistory(@PathVariable("explorerId") String explorerId) {
        return spotExploration.findVisitHistory(explorerId);
    }

    @GetMapping("/{spotId}/rate")
    @ResponseStatus(HttpStatus.OK)
    public double getAverageRate(@PathVariable("spotId") String spotId) {
        double avg = spotStatisticsRepository.findBySpotId(spotId)
                .orElseThrow(() -> new SpotLocalizeException("Cannot load statistics for Spot: " + spotId))
                .getRatesAvg();

        if (Double.isNaN(avg))
            return 0;

        return avg;
    }

    @GetMapping("/{spotId}/visitable")
    @ResponseStatus(HttpStatus.OK)
    public boolean isSpotVisitableNow(@PathVariable("spotId") String spotId) {
        return spotInformationManager.isSpotVisitableNow(spotId);
    }

}
