package com.smartexplorer.core.domain.subject.spot.opinion;

/**
 * @Author
 * Karol Meksuła
 * 18-06-2018
 * */

public class DefaultOpinionValidator implements OpinionValidator {

    @Override
    public boolean validateOpinion(Opinion opinion) {
        return opinion.getContent().length() < 300
                && opinion.getRate() >= 1 && opinion.getRate() <= 5
                && !opinion.getSpotId().isEmpty()
                && !opinion.getExplorerId().isEmpty();
    }

}
