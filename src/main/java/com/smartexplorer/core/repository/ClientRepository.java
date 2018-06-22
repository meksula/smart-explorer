package com.smartexplorer.core.repository;

import com.smartexplorer.core.domain.subject.client.ProxyClient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @Author
 * Karol Meksuła
 * 21-06-2018
 * */

public interface ClientRepository extends MongoRepository<ProxyClient, String> {
    Optional<ProxyClient> findByClientName(String clientName);
}
