package com.smartexplorer.core.domain.subject.spotmaker.auth;

/**
 * @Author
 * Karol Meksuła
 * 03-07-2018
 * */

public interface SpotMakerAuthHelper {
    boolean hasSpotRights(String spotId, String username);
}
