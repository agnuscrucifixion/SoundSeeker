package ru.padwicki.soundseeker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.padwicki.soundseeker.config.auth.SpotifyAuth;
import ru.padwicki.soundseeker.serviceInterfaces.HubServiceInterface;

import java.net.URI;

@Service
public class HubService implements HubServiceInterface {
    SpotifyAuth spotifyAuth;

    @Autowired
    public HubService(SpotifyAuth spotifyAuth) {
        this.spotifyAuth = spotifyAuth;
    }
    @Override
    public URI authorization() {
        return spotifyAuth.authorization();
    }
}
