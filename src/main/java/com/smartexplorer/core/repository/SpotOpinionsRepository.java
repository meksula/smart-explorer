package com.smartexplorer.core.repository;

import com.smartexplorer.core.domain.subject.spot.opinion.Opinion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @Author
 * Karol Meksuła
 * 18-06-2018
 * */

public interface SpotOpinionsRepository extends MongoRepository<Opinion, String> {
    List<Opinion> findAllBySpotId(String spotId);

    List<Opinion> findAllByExplorerId(String explorerId);
}
