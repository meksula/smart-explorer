package com.smartexplorer.core.controller.command;

/**
 * @Author
 * Karol Meksuła
 * 30-06-2018
 *
 * Command that is responsible for sending email to appropriate SpotMaker
 *
 * */

public interface StatisticsMailCommand {
    void sendStatistics(String spotId);
}
