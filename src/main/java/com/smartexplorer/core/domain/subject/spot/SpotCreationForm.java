package com.smartexplorer.core.domain.subject.spot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * @Author
 * Karol Meksuła
 * 14-06-2018
 * */

@Getter
public class SpotCreationForm {
    private String spotMakerId;
    private String name;
    private String description;
    private boolean searchEnable;

    private String city;
    private String street;
    private String buildingNumber;

    @JsonCreator
    public SpotCreationForm(@JsonProperty("name") String name,
                            @JsonProperty("description") String description,
                            @JsonProperty("searchEnable") boolean searchEnable,
                            @JsonProperty("city") String city,
                            @JsonProperty("street") String street,
                            @JsonProperty("buildingNumber") String buildingNumber) {
        this.name = name;
        this.description = description;
        this.searchEnable = searchEnable;
        this.city = city;
        this.street = street;
        this.buildingNumber = buildingNumber;
    }

    public void setSpotMakerId(String spotMakerId) {
        this.spotMakerId = spotMakerId;
    }
}
