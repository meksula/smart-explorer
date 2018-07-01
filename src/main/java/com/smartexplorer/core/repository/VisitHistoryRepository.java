package com.smartexplorer.core.repository;

import com.smartexplorer.core.domain.subject.explorers.Visit;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @Author
 * Karol Meksuła
 * 18-06-2018
 * */

public interface VisitHistoryRepository extends MongoRepository<Visit, String> {
    List<Visit> findAllByExplorerId(String explorerId);

    List<Visit> findAllBySpotId(String spotId);
}
