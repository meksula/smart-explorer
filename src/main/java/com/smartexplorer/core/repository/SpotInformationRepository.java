package com.smartexplorer.core.repository;

import com.smartexplorer.core.domain.subject.spot.information.SpotInformation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @Author
 * Karol Meksuła
 * 03-07-2018
 * */

public interface SpotInformationRepository extends MongoRepository<SpotInformation, String> {
    Optional<SpotInformation> findBySpotId(String spotId);
}
