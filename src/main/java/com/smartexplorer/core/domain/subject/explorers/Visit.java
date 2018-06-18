package com.smartexplorer.core.domain.subject.explorers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.joda.time.LocalDateTime;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author
 * Karol Meksuła
 * 18-06-2018
 * */

@Getter
@Document(collection = "visitHistory")
public class Visit {
    private String spotId;
    private String explorerId;
    private String date;

    public Visit(@JsonProperty("spotId") String spotId,
                 @JsonProperty("explorerId") String explorerId) {
        this.spotId = spotId;
        this.explorerId = explorerId;
    }

    public void initDate() {
        this.date = LocalDateTime.now().toString();
    }

}
