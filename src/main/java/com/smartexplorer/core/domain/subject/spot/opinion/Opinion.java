package com.smartexplorer.core.domain.subject.spot.opinion;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.joda.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author
 * Karol Meksuła
 * 17-06-2018
 * */

@Getter
@Document(collection = "opinions")
public class Opinion {

    @Id
    private String id;

    private String explorerId;
    private String spotId;
    private double rate;
    private String content;
    private String date;

    @JsonCreator
    public Opinion(@JsonProperty("explorerId") String explorerId,
                   @JsonProperty("spotId") String spotId,
                   @JsonProperty("rate") double rate,
                   @JsonProperty("content") String content) {
        this.explorerId = explorerId;
        this.spotId = spotId;
        this.rate = rate;
        this.content = content;
    }

    public void initDate() {
        this.date = LocalDateTime.now().toString();
    }

}
