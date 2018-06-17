package com.smartexplorer.core.repository;

import com.smartexplorer.core.domain.subject.spot.Spot;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * @Author
 * Karol Meksuła
 * 14-06-2018
 * */

public interface SpotRepository extends MongoRepository<Spot, String> {
    Optional<Spot> findByName(String name);

    List<Spot> findByCity(String city);
}
