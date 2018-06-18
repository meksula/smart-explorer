package com.smartexplorer.core.repository;

import com.smartexplorer.core.domain.subject.spot.stats.SpotStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @Author
 * Karol Meksuła
 * 17-06-2018
 * */

public interface SpotStatisticsRepository extends MongoRepository<SpotStatistics, String> {
    Optional<SpotStatistics> findBySpotId(String spotId);
}
