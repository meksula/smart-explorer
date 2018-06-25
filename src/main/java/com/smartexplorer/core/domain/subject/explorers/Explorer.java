package com.smartexplorer.core.domain.subject.explorers;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.smartexplorer.core.domain.mail.MailReceiver;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author
 * Karol Meksuła
 * 18-06-2018
 * */

@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Document(collection = "explorers")
public class Explorer implements MailReceiver {

    @Id
    private String explorerId;

    private String nickname;
    private String email;
    private String name;
    private String surname;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
