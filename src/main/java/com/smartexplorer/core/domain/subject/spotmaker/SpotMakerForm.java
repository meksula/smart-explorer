package com.smartexplorer.core.domain.subject.spotmaker;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;

/**
 * @Author
 * Karol Meksuła
 * 10-06-2018
 * */

@Getter
public class SpotMakerForm implements Serializable {
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private int age;

    @JsonCreator
    public SpotMakerForm(@JsonProperty("username") String username,
                         @JsonProperty("password") String password,
                         @JsonProperty("name") String name,
                         @JsonProperty("surname") String surname,
                         @JsonProperty("email") String email,
                         @JsonProperty("age") int age) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.age = age;
    }

}
