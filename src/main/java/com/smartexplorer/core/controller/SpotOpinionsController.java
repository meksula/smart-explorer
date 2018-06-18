package com.smartexplorer.core.controller;

import com.smartexplorer.core.domain.subject.spot.opinion.DefaultOpinionValidator;
import com.smartexplorer.core.domain.subject.spot.opinion.Opinion;
import com.smartexplorer.core.domain.subject.spot.opinion.OpinionValidator;
import com.smartexplorer.core.domain.subject.spot.opinion.OpinionsProvider;
import com.smartexplorer.core.domain.subject.spot.stats.OpinionStat;
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

    @OpinionStat
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Opinion addSpotOpinion(@RequestBody Opinion opinion) {
        if (opinionValidator.validateOpinion(opinion)) {
            opinion.initDate();
            return spotOpinionsRepository.save(opinion);
        }

        else throw new IllegalArgumentException();
    }

    @GetMapping("/latest/{spotId}/{amount}")
    @ResponseStatus(HttpStatus.OK)
    public List<Opinion> getLatestOpinions(@PathVariable("spotId") String spotId,
                                           @PathVariable("amount") int amount) {
        return opinionsProvider.getLastAdded(spotId, amount);
    }

    @GetMapping("/best/{spotId}/{amount}")
    @ResponseStatus(HttpStatus.OK)
    public List<Opinion> getBestOpinions(@PathVariable("spotId") String spotId,
                                         @PathVariable("amount") int amount) {
        return opinionsProvider.getBestOpinions(spotId, amount);
    }

    @GetMapping("/worst/{spotId}/{amount}")
    @ResponseStatus(HttpStatus.OK)
    public List<Opinion> getWorstOpinions(@PathVariable("spotId") String spotId,
                                          @PathVariable("amount") int amount) {
        return opinionsProvider.getWorstOpinions(spotId, amount);
    }

    @GetMapping("/explorer/{explorerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Opinion> getExplorersOpinions(@PathVariable("explorerId") String explorerId) {
        return opinionsProvider.getExplorersOpinions(explorerId);
    }

    @GetMapping("/all/{spotId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Opinion> getAllOpinions(@PathVariable("spotId") String spotId) {
        return opinionsProvider.getAllOpinions(spotId);
    }

}
