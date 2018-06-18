package com.smartexplorer.core.domain.subject.spot.stats;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author
 * Karol Meksuła
 * 14-06-2018
 * */

@Getter
@Setter
@Document(collection = "statistics")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SpotStatistics {

    @Id
    private String id;

    private String spotId;
    private double ratesAvg;
    private double ratesTotal;
    private int ratesNumber;
    private long visitNumber;
    private long searches;

    public SpotStatistics(String spotId) {
        this.spotId = spotId;
    }

}
