package com.smartexplorer.core.controller;

import com.smartexplorer.core.domain.subject.spot.Spot;
import com.smartexplorer.core.domain.subject.spot.SpotCreationForm;
import com.smartexplorer.core.domain.subject.spot.SpotCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @Author
 * Karol Meksuła
 * 14-06-2018
 * */

@RestController
@RequestMapping("/api/v1/spot")
public class SpotController {
    private SpotCreator spotCreator;

    @Autowired
    public void setSpotCreator(SpotCreator spotCreator) {
        this.spotCreator = spotCreator;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Spot createSpot(@RequestBody SpotCreationForm spotCreationForm) {
        return spotCreator.createSpot(spotCreationForm);
    }

}
