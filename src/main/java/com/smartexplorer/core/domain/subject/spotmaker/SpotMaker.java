package com.smartexplorer.core.domain.subject.spotmaker;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.smartexplorer.core.domain.mail.MailReceiver;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author
 * Karol Meksuła
 * 10-06-2018
 * */

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Document(collection = "spotmaker")
public class SpotMaker implements Serializable, MailReceiver, UserDetails {

    @Id
    private String spotMakerId;

    private boolean confirmed;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private int age;
    private String[] authorities;

    public SpotMaker() {}

    public SpotMaker(SpotMakerBuilder builder) {
        this.spotMakerId = builder.spotMakerId;
        this.confirmed = builder.confirmed;
        this.username = builder.username;
        this.password = builder.password;
        this.name = builder.name;
        this.surname = builder.surname;
        this.email = builder.email;
        this.age = builder.age;
        this.authorities = builder.authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (String auth : authorities) {
            authorityList.add(new SimpleGrantedAuthority(auth));
        }

        return authorityList;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return confirmed;
    }

    public static class SpotMakerBuilder {
        private String spotMakerId;
        private boolean confirmed;
        private String username;
        private String password;
        private String name;
        private String surname;
        private String email;
        private int age;
        private String[] authorities;

        public SpotMakerBuilder spotMakerId(String spotMakerId) {
            this.spotMakerId = spotMakerId;
            return this;
        }

        public SpotMakerBuilder isConfirmed(boolean confirmed) {
            this.confirmed = confirmed;
            return this;
        }

        public SpotMakerBuilder authorities(String[] authorities) {
            this.authorities = authorities;
            return this;
        }

        public SpotMakerBuilder username(String username) {
            this.username = username;
            return this;
        }

        public SpotMakerBuilder password(String password) {
            this.password = password;
            return this;
        }

        public SpotMakerBuilder name(String name) {
            this.name = name;
            return this;
        }

        public SpotMakerBuilder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public SpotMakerBuilder email(String email) {
            this.email = email;
            return this;
        }

        public SpotMakerBuilder age(int age) {
            this.age = age;
            return this;
        }

        public SpotMaker build() {
            return new SpotMaker(this);
        }
    }
}
