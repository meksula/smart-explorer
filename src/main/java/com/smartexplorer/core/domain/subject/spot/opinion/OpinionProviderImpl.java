package com.smartexplorer.core.domain.subject.spot.opinion;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author
 * Karol Meksuła
 * 18-06-2018
 * */

@Service
public class OpinionProviderImpl implements OpinionsProvider {
    @Override
    public List<Opinion> getLastAdded(String spotId, int amount) {
        return null;
    }

    @Override
    public List<Opinion> getWorstOpinions(String spotId, int amount) {
        return null;
    }

    @Override
    public List<Opinion> getBestOpinions(String spotId, int amount) {
        return null;
    }

    @Override
    public List<Opinion> getExplorersOpinions(String explorerId) {
        return null;
    }

    @Override
    public List<Opinion> getAllOpinions(String spotId) {
        return null;
    }
}
