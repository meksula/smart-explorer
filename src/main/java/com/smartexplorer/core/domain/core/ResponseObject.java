package com.smartexplorer.core.domain.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartexplorer.core.domain.subject.spot.Spot;
import lombok.Getter;
import org.joda.time.DateTime;

/**
 * @Author
 * Karol Meksuła
 * 08-06-2018
 * */

@Getter
public class ResponseObject {
    private DateTime date;
    private Spot spot;

    @JsonCreator
    public ResponseObject(@JsonProperty("spot") Spot spot) {
        this.date = DateTime.now();

        this.spot = spot;
    }

}
