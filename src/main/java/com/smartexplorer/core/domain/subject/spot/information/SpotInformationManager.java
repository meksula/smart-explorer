package com.smartexplorer.core.domain.subject.spot.information;

/**
 * @Author
 * Karol Meksuła
 * 03-07-2018
 * */

public interface SpotInformationManager {
    SpotInformation createEmptyInfo(String spotId);

    SpotInformation modifySpotInformation(SpotInformation spotInformation);

    boolean isSpotVisitableNow(String spotId);

}
