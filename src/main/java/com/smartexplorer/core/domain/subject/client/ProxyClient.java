package com.smartexplorer.core.domain.subject.client;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @Author
 * Karol Meksuła
 * 21-06-2018
 * */

@Getter
@Document(collection = "client")
public class ProxyClient implements Client, UserDetails {

    @Id
    private String clientId;

    private String clientName;
    private String key;

    public ProxyClient(String clientName, String key) {
        this.clientName = clientName;
        this.key = key;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(Collections.singletonList(new SimpleGrantedAuthority("PROXY")));
    }

    @Override
    public String getPassword() {
        return key;
    }

    @Override
    public String getUsername() {
        return clientName;
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
        return true;
    }
}
