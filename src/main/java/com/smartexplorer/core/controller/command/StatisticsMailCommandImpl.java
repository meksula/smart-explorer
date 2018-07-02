package com.smartexplorer.core.controller.command;

import com.smartexplorer.core.domain.mail.MailSender;
import com.smartexplorer.core.domain.mail.MailType;
import com.smartexplorer.core.domain.subject.spot.Spot;
import com.smartexplorer.core.domain.subject.spot.stats.StatisticsProvider;
import com.smartexplorer.core.domain.subject.spotmaker.SpotMaker;
import com.smartexplorer.core.exception.SpotLocalizeException;
import com.smartexplorer.core.repository.SpotMakerRepository;
import com.smartexplorer.core.repository.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author
 * Karol Meksuła
 * 01-07-2018
 * */

@Service
public class StatisticsMailCommandImpl implements StatisticsMailCommand {
    private MailSender mailSender;
    private StatisticsProvider statisticsProvider;
    private SpotMakerRepository spotMakerRepository;
    private SpotRepository spotRepository;

    @Autowired
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Autowired
    public void setStatisticsProvider(StatisticsProvider statisticsProvider) {
        this.statisticsProvider = statisticsProvider;
    }

    @Autowired
    public void setSpotMakerRepository(SpotMakerRepository spotMakerRepository) {
        this.spotMakerRepository = spotMakerRepository;
    }

    @Autowired
    public void setSpotRepository(SpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }

    @Override
    public void sendStatistics(String spotId) {
        mailSender.setAttachment(statisticsProvider.spotSummary(spotId));

        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new SpotLocalizeException("Exception: there is no spot with spotId: " + spotId));

        SpotMaker spotMaker = spotMakerRepository.findById(spot.getSpotMakerId())
                .orElseThrow(() -> new SpotLocalizeException("Exception: there is no SpotMaker with id: " + spot.getSpotMakerId()));

        mailSender.sendMail(MailType.SPOT_STATISTIC_MAIL, spotMaker);
    }

}
