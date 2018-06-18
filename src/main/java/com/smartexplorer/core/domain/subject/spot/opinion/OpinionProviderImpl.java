package com.smartexplorer.core.domain.subject.spot.opinion;

import com.smartexplorer.core.repository.SpotOpinionsRepository;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * @Author
 * Karol Meksuła
 * 18-06-2018
 * */

@Service
public class OpinionProviderImpl implements OpinionsProvider {
    private SpotOpinionsRepository spotOpinionsRepository;

    @Autowired
    public void setSpotOpinionsRepository(SpotOpinionsRepository spotOpinionsRepository) {
        this.spotOpinionsRepository = spotOpinionsRepository;
    }

    @Override
    public List<Opinion> getLastAdded(String spotId, int amount) {
        List<Opinion> opinionList = spotOpinionsRepository.findAllBySpotId(spotId);
        try {
            opinionList.sort((o1, o2) -> {
                if (LocalDateTime.parse(o1.getDate()).isAfter(LocalDateTime.parse(o2.getDate())))
                    return -1;
                else if (LocalDateTime.parse(o1.getDate()).isBefore(LocalDateTime.parse(o2.getDate())))
                    return 1;
                else return 0;
            });
            return opinionList.subList(0, amount);
        } catch (IndexOutOfBoundsException e) {
            return opinionList;
        }

    }

    @Override
    public List<Opinion> getWorstOpinions(String spotId, int amount) {
        List<Opinion> opinionList = spotOpinionsRepository.findAllBySpotId(spotId);
        opinionList.sort(Comparator.comparing(Opinion::getRate).reversed());
        try {
            return opinionList.subList(0, amount);
        } catch (IndexOutOfBoundsException e) {
            return opinionList;
        }
    }

    @Override
    public List<Opinion> getBestOpinions(String spotId, int amount) {
        List<Opinion> opinionList = spotOpinionsRepository.findAllBySpotId(spotId);
        opinionList.sort((o1, o2) -> Double.compare(o2.getRate(), o1.getRate()));
        try {
            return opinionList.subList(0, amount);
        } catch (IndexOutOfBoundsException e) {
            return opinionList;
        }
    }

    @Override
    public List<Opinion> getExplorersOpinions(String explorerId) {
        return spotOpinionsRepository.findAllByExplorerId(explorerId);
    }

    @Override
    public List<Opinion> getAllOpinions(String spotId) {
        return spotOpinionsRepository.findAllBySpotId(spotId);
    }
}
