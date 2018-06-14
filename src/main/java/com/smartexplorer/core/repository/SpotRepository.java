package com.smartexplorer.core.repository;

import com.smartexplorer.core.domain.subject.spot.Spot;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author
 * Karol Meksuła
 * 14-06-2018
 * */

public interface SpotRepository extends MongoRepository<Spot, String> {
}
