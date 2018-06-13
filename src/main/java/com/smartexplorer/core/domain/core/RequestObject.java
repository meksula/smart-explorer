package com.smartexplorer.core.domain.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;

/**
 * @Author
 * Karol Meksuła
 * 08-06-2018
 * */

@Getter
public class RequestObject implements Serializable {
    private double accurancy;
    private double latitude;
    private double longitude;
    private CustomPreferences customPreferences;

    @JsonCreator
    public RequestObject(@JsonProperty("accurancy") double accurancy,
                         @JsonProperty("latitude") double latitude,
                         @JsonProperty("longitude") double longitude,
                         @JsonProperty("customPreferences") CustomPreferences customPreferences) {
        this.accurancy = accurancy;
        this.latitude = latitude;
        this.longitude = longitude;
        this.customPreferences = customPreferences;
    }

}
