package com.smartexplorer.core.repository;

import com.smartexplorer.core.domain.subject.spotmaker.SpotMaker;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @Author
 * Karol Meksuła
 * 11-06-2018
 * */

public interface SpotMakerRepository extends MongoRepository<SpotMaker, String> {
    Optional<SpotMaker> findByUsername(String username);

    Optional<SpotMaker> findByEmail(String email);
}
