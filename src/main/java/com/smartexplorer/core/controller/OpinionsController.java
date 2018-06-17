package com.smartexplorer.core.controller;

import com.smartexplorer.core.domain.subject.spot.opinion.Opinion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author
 * Karol Meksuła
 * 17-06-2018
 * */

@RestController
@RequestMapping("/api/v1/opinions")
public class OpinionsController {

    @GetMapping("/{spotId}")
    public Opinion getSpotOpinion() {
        return null;
    }

}
