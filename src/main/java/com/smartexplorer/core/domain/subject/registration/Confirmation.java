package com.smartexplorer.core.domain.subject.registration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author
 * Karol Meksuła
 * 11-06-2018
 * */

@Getter
@Document(collection = "confirmation")
public class Confirmation {

    @Id
    private String id;

    private String spotMakerId;
    private String verificationCode;

    public Confirmation(@JsonProperty("spotMakerId") String spotMakerId,
                        @JsonProperty("verificationCode") String verificationCode) {
        this.spotMakerId = spotMakerId;
        this.verificationCode = verificationCode;
    }

    public void setId(String id) {
        this.id = id;
    }

}
