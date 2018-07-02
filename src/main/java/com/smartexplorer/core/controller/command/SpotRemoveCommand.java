package com.smartexplorer.core.controller.command;

import com.smartexplorer.core.domain.subject.spot.Spot;

/**
 * @Author
 * Karol Meksuła
 * 02-07-2018
 * */

public interface SpotRemoveCommand {
    Spot removeSpot(String username, String spotId);
}
