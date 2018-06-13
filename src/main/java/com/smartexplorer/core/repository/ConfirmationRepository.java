package com.smartexplorer.core.repository;


import com.smartexplorer.core.domain.subject.registration.Confirmation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


/**
 * @Author
 * Karol Meksuła
 * 11-06-2018
 * */

public interface ConfirmationRepository extends MongoRepository<Confirmation, String> {
    Optional<Confirmation> findBySpotMakerId(String spotMakerId);

    void deleteBySpotMakerId(String spotMakerId);
}
