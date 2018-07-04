package com.smartexplorer.core.domain.subject.spot.information;

import com.smartexplorer.core.exception.SpotLocalizeException;
import com.smartexplorer.core.repository.SpotInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author
 * Karol Meksuła
 * 03-07-2018
 * */

@Service
public class SpotInformationManagerImpl implements SpotInformationManager {
    private SpotInformationRepository spotInformationRepository;

    @Autowired
    public void setSpotInformationRepository(SpotInformationRepository spotInformationRepository) {
        this.spotInformationRepository = spotInformationRepository;
    }

    @Override
    public SpotInformation createEmptyInfo(String spotId) {
        SpotInformation spotInformation = new SpotInformation(spotId);
        Map<DayOfWeek, VisitDay> visitDayMap = new LinkedHashMap<>();

        for (int i = 1; i < 8; i++) {
            visitDayMap.put(DayOfWeek.of(i), new VisitDay());
        }

        spotInformation.setVisitDaysInWeek(visitDayMap);
        return spotInformationRepository.save(spotInformation);
    }

    @Override
    public SpotInformation modifySpotInformation(SpotInformation spotInformation) {
        spotInformationRepository.delete(spotInformation);
        return spotInformationRepository.save(spotInformation);
    }

    @Override
    public boolean isSpotVisitableNow(String spotId) {
        SpotInformation spotInformation = spotInformationRepository.findBySpotId(spotId)
                .orElseThrow(() -> new SpotLocalizeException("Cannot load information about spot with id: " + spotId));

        LocalDateTime now = LocalDateTime.now();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        int hour = now.getHour();

        try {
            VisitDay visitDay = spotInformation.getVisitDaysInWeek().get(dayOfWeek);

            for (int visitHour : visitDay.getHours()) {
                if (visitHour == hour)
                    return true;
            }
        } catch (NullPointerException npo) {
            return false;
        }

        return false;
    }

}
