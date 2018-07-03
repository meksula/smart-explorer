package com.smartexplorer.core.domain.subject.spot.information;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Map;

/**
 * @Author
 * Karol Meksuła
 * 03-07-2018
 * */

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Document(collection = "spotinformation")
public class SpotInformation {
    private String spotId;
    private Map<DayOfWeek, VisitDay> visitDaysInWeek;
    private BigDecimal normalTicketPrice;
    private BigDecimal discountedTicketPrice;
    private String message;

    public SpotInformation(String spotId) {
        this.spotId = spotId;
    }

}
