package com.smartexplorer.core.domain.subject.spot.opinion;

import java.util.List;

/**
 * @Author
 * Karol Meksuła
 * 18-06-2018
 * */

public interface OpinionsProvider {
    List<Opinion> getLastAdded(String spotId, int amount);

    List<Opinion> getWorstOpinions(String spotId, int amount);

    List<Opinion> getBestOpinions(String spotId, int amount);

    List<Opinion> getExplorersOpinions(String explorerId);

    List<Opinion> getAllOpinions(String spotId);
}
