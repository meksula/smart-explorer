package com.smartexplorer.core.domain.subject.spot;

import com.smartexplorer.core.repository.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author
 * Karol Meksuła
 * 14-06-2018
 * */

@Service
public class SpotCreatorImpl extends SpotCreator {
    private SpotRepository spotRepository;

    @Autowired
    public void setSpotRepository(SpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }
}
