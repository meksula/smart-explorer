package com.smartexplorer.core.controller;

import com.smartexplorer.core.domain.subject.spot.opinion.DefaultOpinionValidator;
import com.smartexplorer.core.domain.subject.spot.opinion.Opinion;
import com.smartexplorer.core.domain.subject.spot.opinion.OpinionValidator;
import com.smartexplorer.core.domain.subject.spot.opinion.OpinionsProvider;
import com.smartexplorer.core.repository.SpotOpinionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author
 * Karol Meksuła
 * 18-06-2018
 * */

@RestController
@RequestMapping("/api/v1/spot/opinions")
public class SpotOpinionsController {
    private SpotOpinionsRepository spotOpinionsRepository;
    private OpinionValidator opinionValidator;
    private OpinionsProvider opinionsProvider;

    public SpotOpinionsController() {
        this.opinionValidator = new DefaultOpinionValidator();
    }

    @Autowired
    public void setSpotOpinionsRepository(SpotOpinionsRepository spotOpinionsRepository) {
        this.spotOpinionsRepository = spotOpinionsRepository;
    }

    @Autowired
    public void setOpinionsProvider(OpinionsProvider opinionsProvider) {
        this.opinionsProvider = opinionsProvider;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Opinion addSpotOpinion(@RequestBody Opinion opinion) {
        if (opinionValidator.validateOpinion(opinion))
            return spotOpinionsRepository.save(opinion);

        else throw new IllegalArgumentException();
    }

    @GetMapping("/latest/{spotId}/{amount}")
    @ResponseStatus(HttpStatus.OK)
    public List<Opinion> getLatestOpinions(@PathVariable("spotId") String spotId,
                                           @PathVariable("amount") int amount) {
        return opinionsProvider.getLastAdded(spotId, amount);
    }

}
