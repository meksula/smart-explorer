package com.smartexplorer.core.domain.subject.spot;

import com.google.maps.model.GeocodingResult;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author
 * Karol Meksuła
 * 10-06-2018
 * */

@Getter
@Document(collection = "spot")
public class Spot {

    @Id
    private String id;

    private String spotMakerId;
    private String creationDate;
    private String name;
    private String description;
    private SpotStatistics spotStatistics;
    private GeocodingResult geocodingResult;
}
