package com.smartexplorer.core.domain.subject.spot;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
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
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Document(collection = "spot")
public class Spot {

    @Id
    private String id;

    private String spotMakerId;
    private String creationDate;
    private String name;
    private String description;
    private GeocodingResult[] geocodingResult;
    private boolean searchEnable;

    double longitude;
    double latitude;

    String country;
    String zipCode;
    String district;
    String city;
    String street;
    String buildingNumber;

    public Spot(String spotMakerId, String creationDate, GeocodingResult[] geocodingResult) {
        this.spotMakerId = spotMakerId;
        this.creationDate = creationDate;
        this.geocodingResult = geocodingResult;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSearchEnable(boolean searchEnable) {
        this.searchEnable = searchEnable;
    }

}
