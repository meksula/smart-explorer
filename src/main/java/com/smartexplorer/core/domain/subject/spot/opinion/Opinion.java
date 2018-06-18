package com.smartexplorer.core.domain.subject.spot.opinion;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
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
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Document(collection = "opinions")
public class Opinion {

    @Id
    private String id;

    private String explorerId;
    private String spotId;
    private double rate;
    private String content;
    private String date;

    public Opinion(String explorerId, String spotId, double rate, String content) {
        this.explorerId = explorerId;
        this.spotId = spotId;
        this.rate = rate;
        this.content = content;
    }

    public void initDate() {
        this.date = LocalDateTime.now().toString();
    }

}
