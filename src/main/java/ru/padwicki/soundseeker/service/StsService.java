package ru.padwicki.soundseeker.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.padwicki.soundseeker.config.auth.CredentialsClient;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;
import org.apache.hc.core5.http.ParseException;
import ru.padwicki.soundseeker.serviceInterfaces.StSServiceInterface;

import java.io.IOException;

@Service
public class StsService implements StSServiceInterface, InitializingBean {
    CredentialsClient credentialsClient;
    @Autowired
    public StsService(CredentialsClient credentialsClient) {
        this.credentialsClient = credentialsClient;
    }
    private SpotifyApi spotifyApi;
    @Override
    public void getTrack(String id) throws IOException, ParseException, SpotifyWebApiException {
        final Track track = find(id);
        System.out.println("Name: " + track.getName());
    }
    private Track find(String id) throws IOException, ParseException, SpotifyWebApiException {
        GetTrackRequest getTrackRequest = spotifyApi.getTrack(id)
                .build();
        return getTrackRequest.execute();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String accessToken = credentialsClient.clientCredentials();
        spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken)
                .build();
    }
}
