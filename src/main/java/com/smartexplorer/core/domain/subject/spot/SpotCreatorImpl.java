package com.smartexplorer.core.domain.subject.spot;

import com.google.maps.model.GeocodingResult;
import com.smartexplorer.core.domain.geolocation.BasicAddress;
import com.smartexplorer.core.domain.geolocation.Geolocation;
import com.smartexplorer.core.domain.mail.MailSender;
import com.smartexplorer.core.domain.mail.MailType;
import com.smartexplorer.core.domain.subject.spot.stats.SpotStatistics;
import com.smartexplorer.core.domain.subject.spotmaker.SpotMaker;
import com.smartexplorer.core.exception.SpotCreationException;
import com.smartexplorer.core.repository.SpotMakerRepository;
import com.smartexplorer.core.repository.SpotRepository;
import com.smartexplorer.core.repository.SpotStatisticsRepository;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author
 * Karol Meksuła
 * 14-06-2018
 * */

@Service
public class SpotCreatorImpl extends SpotCreator {
    private SpotRepository spotRepository;
    private SpotStatisticsRepository spotStatisticsRepository;
    private Geolocation geolocation;
    private MailSender mailSender;
    private SpotMakerRepository spotMakerRepository;

    @Autowired
    public void setSpotRepository(SpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }

    @Autowired
    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
    }

    @Autowired
    public void setSpotStatisticsRepository(SpotStatisticsRepository spotStatisticsRepository) {
        this.spotStatisticsRepository = spotStatisticsRepository;
    }

    @Autowired
    public void setSpotMakerRepository(SpotMakerRepository spotMakerRepository) {
        this.spotMakerRepository = spotMakerRepository;
    }

    @Autowired
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    protected boolean validateForm(SpotCreationForm spotCreationForm) {
        return !spotCreationForm.getCity().isEmpty()
                && !spotCreationForm.getStreet().isEmpty()
                && !spotCreationForm.getBuildingNumber().isEmpty();
    }

    @Override
    protected GeocodingResult[] geolocation(SpotCreationForm spotCreationForm) {
        return geolocation.geolocateByAddress(buildAddress(spotCreationForm));
    }

    private BasicAddress buildAddress(SpotCreationForm spotCreationForm) {
        return new BasicAddress(spotCreationForm.getCity(),
                spotCreationForm.getStreet(),
                spotCreationForm.getBuildingNumber());
    }

    @Override
    protected Spot buildSpot(SpotCreationForm spotCreationForm, GeocodingResult[] geocodigResults) {
        Spot spot = new Spot(spotCreationForm.getSpotMakerId(), LocalDate.now().toString(), geocodigResults);
        spot.setName(spotCreationForm.getName());
        spot.setDescription(spotCreationForm.getDescription());
        spot.setSearchEnable(spotCreationForm.isSearchEnable());

        GeocodingResult result = geocodigResults[0];

        spot.longitude = result.geometry.location.lng;
        spot.latitude = result.geometry.location.lat;

        if (result.addressComponents.length == 8) {
            spot.buildingNumber = result.addressComponents[0].longName;
            spot.street = result.addressComponents[1].longName;
            spot.district = result.addressComponents[3].longName;
            spot.city = result.addressComponents[4].longName;
            spot.country = result.addressComponents[6].longName;
            spot.zipCode = result.addressComponents[7].longName;
        }
        else if (result.addressComponents.length == 7) {
            spot.buildingNumber = result.addressComponents[0].longName;
            spot.street = result.addressComponents[1].longName;
            spot.district = result.addressComponents[2].longName;
            spot.city = result.addressComponents[3].longName;
            spot.country = result.addressComponents[5].longName;
            spot.zipCode = result.addressComponents[6].longName;
        }
        else if (result.addressComponents.length < 7) {
            spot.buildingNumber = result.addressComponents[0].longName;
            spot.street = null;
            spot.district = result.addressComponents[2].longName;
            spot.city = result.addressComponents[1].longName;
            spot.country = result.addressComponents[4].longName;
            spot.zipCode = result.addressComponents[5].longName;
        }

        return spot;
    }

    @Override
    protected void saveSpot(Spot spot) {
        spotRepository.save(spot);
    }

    @Override
    protected void createStatsEntity(String spotId) {
        SpotStatistics spotStatistics = new SpotStatistics(spotId);
        spotStatisticsRepository.save(spotStatistics);
    }

    @Override
    protected void sendEmail(Spot spot) {
        SpotMaker spotMaker = spotMakerRepository.findById(spot.getSpotMakerId())
                .orElseThrow(() -> new SpotCreationException("Cannot retrieve email from SpotMaker!"));

        mailSender.setAttachment(spot);
        mailSender.sendMail(MailType.SPOT_CREATION_CONFIRM, spotMaker);
    }

}
