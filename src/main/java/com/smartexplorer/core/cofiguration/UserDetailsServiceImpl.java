package com.smartexplorer.core.cofiguration;

import com.smartexplorer.core.domain.subject.client.ProxyClient;
import com.smartexplorer.core.repository.ClientRepository;
import com.smartexplorer.core.repository.SpotMakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author
 * Karol Meksuła
 * 13-06-2018
 * */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private SpotMakerRepository spotMakerRepository;
    private ClientRepository clientRepository;

    @Value("${proxy.client.api}")
    private String proxyKey;

    @Autowired
    public void setSpotMakerRepository(SpotMakerRepository spotMakerRepository) {
        this.spotMakerRepository = spotMakerRepository;
    }

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals(proxyKey))
            return proxyClientAccess(username);

        return spotMakerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    private UserDetails proxyClientAccess(String client) {
        return clientRepository.save(new ProxyClient(client, new BCryptPasswordEncoder().encode(proxyKey)));
    }

}
