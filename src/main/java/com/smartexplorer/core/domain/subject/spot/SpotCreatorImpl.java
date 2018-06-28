package com.smartexplorer.core.domain.subject.spot;

import com.google.maps.model.GeocodingResult;
import com.smartexplorer.core.domain.core.DefaultAddressComponentMapper;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                && !spotCreationForm.getBuildingNumber().isEmpty()
                && notExist(spotCreationForm);
    }

    private boolean notExist(SpotCreationForm spotCreationForm) {
        List<Spot> spotList = spotRepository.findByCity(spotCreationForm.getCity());

        List<Boolean> conditions = new ArrayList<>();

        for (Spot spot : spotList) {
            String name = spot.getName();
            String buildingNumber = spot.getBuildingNumber();
            String street = spot.getStreet();

            if (!name.equals(spotCreationForm.getName())
                    && !buildingNumber.equals(spotCreationForm.getBuildingNumber())
                    && !street.equals(spotCreationForm.getStreet())) {
                conditions.add(Boolean.TRUE);
            } else conditions.add(Boolean.FALSE);

        }

        for (Boolean condition : conditions) {
            if (!condition) {
                return false;
            }
        }

        return true;

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

        Map<String, String> map = new DefaultAddressComponentMapper().mapAddressComponent(result);

        spot.buildingNumber = map.get("street_number");
        spot.street = map.get("route");
        spot.district = map.get("administrative_area_level_2");
        spot.city = map.get("locality");
        spot.country = map.get("country");
        spot.zipCode = map.get("postal_code");

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
