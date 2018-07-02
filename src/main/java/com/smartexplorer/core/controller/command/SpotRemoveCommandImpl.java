package com.smartexplorer.core.controller.command;

import com.smartexplorer.core.domain.subject.spot.Spot;
import com.smartexplorer.core.domain.subject.spot.stats.SpotStatistics;
import com.smartexplorer.core.domain.subject.spotmaker.SpotMaker;
import com.smartexplorer.core.exception.SpotLocalizeException;
import com.smartexplorer.core.repository.SpotMakerRepository;
import com.smartexplorer.core.repository.SpotRepository;
import com.smartexplorer.core.repository.SpotStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author
 * Karol Meksuła
 * 02-07-2018
 * */

@Service
public class SpotRemoveCommandImpl implements SpotRemoveCommand {
    private SpotMakerRepository spotMakerRepository;
    private SpotRepository spotRepository;
    private SpotStatisticsRepository spotStatisticsRepository;
    private final String RMV_MESSAGE = "Cannot remove spot because its not exist! ";

    @Autowired
    public void setSpotMakerRepository(SpotMakerRepository spotMakerRepository) {
        this.spotMakerRepository = spotMakerRepository;
    }

    @Autowired
    public void setSpotRepository(SpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }

    @Autowired
    public void setSpotStatisticsRepository(SpotStatisticsRepository spotStatisticsRepository) {
        this.spotStatisticsRepository = spotStatisticsRepository;
    }

    @Override
    public Spot removeSpot(String username, String spotId) {
        SpotMaker spotMaker = spotMakerRepository.findByUsername(username)
                .orElseThrow(null);

        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new SpotLocalizeException(RMV_MESSAGE + spotId));

        if (spotMaker.getSpotMakerId().equals(spot.getSpotMakerId())) {
            spotRepository.delete(spot);
            SpotStatistics spotStatistics = spotStatisticsRepository.findBySpotId(spotId)
                    .orElseThrow(() -> new SpotLocalizeException(RMV_MESSAGE + spotId));
            spotStatisticsRepository.delete(spotStatistics);
        }

        return spot;
    }

}
