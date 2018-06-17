package com.smartexplorer.core.controller;

import com.google.maps.model.LatLng;
import com.smartexplorer.core.domain.core.SpotExploration;
import com.smartexplorer.core.domain.subject.spot.Spot;
import com.smartexplorer.core.exception.SpotLocalizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    public void setSpotExploration(SpotExploration spotExploration) {
        this.spotExploration = spotExploration;
    }

    @PostMapping("/nearest")
    @ResponseStatus(HttpStatus.OK)
    public Spot getNearestSpot(@RequestBody LatLng coordinates) {
        return spotExploration.findNearestSpot(coordinates)
                .orElseThrow(() -> new SpotLocalizeException("Cannot localize any spot for your coordinates: "
                        + coordinates.toString()));
    }

    @PostMapping("/city")
    @ResponseStatus(HttpStatus.OK)
    public List<Spot> getSpotListInCity(@RequestBody LatLng coordinates) {
        List<Spot> spotList = spotExploration.findSpotsInCity(coordinates);
        return checkIfEmpty(spotList, "There is no spots in your city.");
    }

    @PostMapping("/district")
    @ResponseStatus(HttpStatus.OK)
    public List<Spot> getSpotListInDistrict(@RequestBody LatLng coordinates) {
        List<Spot> spotList = spotExploration.findSpotsInDistrict(coordinates);
        return checkIfEmpty(spotList, "There is no spots in your district.");
    }

    @PostMapping("/top/{amount}")
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

    @GetMapping("/{spotId}")
    @ResponseStatus(HttpStatus.OK)
    public Spot getSpotById(@PathVariable("spotId") String spotId) {
        return spotExploration.findBySpotId(spotId)
                .orElseThrow(() -> new SpotLocalizeException("There is no spot with id: " + spotId));
    }

}
