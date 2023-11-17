package ru.padwicki.soundseeker.service;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.padwicki.soundseeker.config.auth.SpotifyAuth;
import ru.padwicki.soundseeker.serviceInterfaces.HubServiceInterface;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.net.URI;

@Service
public class HubService implements HubServiceInterface {
    SpotifyAuth spotifyAuth;
    StsService stsService;
    @Autowired
    public HubService(SpotifyAuth spotifyAuth, StsService stsService) {
        this.spotifyAuth = spotifyAuth;
        this.stsService = stsService;
    }
    @Override
    public URI authorization(){
        return spotifyAuth.authorization();
    }
}
