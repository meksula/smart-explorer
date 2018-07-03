package com.smartexplorer.core.domain.subject.spotmaker.auth;

import com.smartexplorer.core.domain.subject.spot.Spot;
import com.smartexplorer.core.domain.subject.spotmaker.SpotMaker;
import com.smartexplorer.core.exception.SpotLocalizeException;
import com.smartexplorer.core.repository.SpotMakerRepository;
import com.smartexplorer.core.repository.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author
 * Karol Meksuła
 * 03-07-2018
 * */

@Service
public class SpotMakerAuthHelperImpl implements SpotMakerAuthHelper {
    private SpotMakerRepository spotMakerRepository;
    private SpotRepository spotRepository;

    @Autowired
    public void setSpotRepository(SpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }

    @Autowired
    public void setSpotMakerRepository(SpotMakerRepository spotMakerRepository) {
        this.spotMakerRepository = spotMakerRepository;
    }

    @Override
    public boolean hasSpotRights(String spotId, String username) {
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new SpotLocalizeException("Cannot find spot with id: " + spotId));

        SpotMaker spotMaker = spotMakerRepository.findByUsername(username)
                .orElseThrow(null);

        return spot.getSpotMakerId().equals(spotMaker.getSpotMakerId());
    }
}
