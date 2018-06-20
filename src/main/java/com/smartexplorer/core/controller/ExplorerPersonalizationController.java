package com.smartexplorer.core.controller;

import com.smartexplorer.core.domain.subject.explorers.Explorer;
import com.smartexplorer.core.repository.ExplorerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author
 * Karol Meksuła
 * 18-06-2018
 * */

@RestController("/api/v1/explorer/personalization")
public class ExplorerPersonalizationController {
    private ExplorerRepository explorerRepository;

    @Autowired
    public void setExplorerRepository(ExplorerRepository explorerRepository) {
        this.explorerRepository = explorerRepository;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Explorer updateExplorer(@RequestBody Explorer explorer) {
        return explorerRepository.save(explorer);
    }

}