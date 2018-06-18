package com.smartexplorer.core.repository;

import com.smartexplorer.core.domain.subject.explorers.Explorer;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author
 * Karol Meksuła
 * 18-06-2018
 * */

public interface ExplorerRepository extends MongoRepository<Explorer, String> {
}
