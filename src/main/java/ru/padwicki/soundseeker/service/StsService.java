package ru.padwicki.soundseeker.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.padwicki.soundseeker.config.auth.CredentialsClient;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumsTracksRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;
import org.apache.hc.core5.http.ParseException;
import ru.padwicki.soundseeker.serviceInterfaces.StSServiceInterface;

import java.io.IOException;
import java.util.Arrays;

@Service
public class StsService implements StSServiceInterface, InitializingBean {
    CredentialsClient credentialsClient;
    @Autowired
    public StsService(CredentialsClient credentialsClient) {
        this.credentialsClient = credentialsClient;
    }
    private SpotifyApi spotifyApi;

    @Override
    public void afterPropertiesSet() throws Exception {
        String accessToken = credentialsClient.clientCredentials();
        spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken)
                .build();
    }



    @Override
    public void getTrack(String id) throws IOException, ParseException, SpotifyWebApiException {
        Track track = findTrack(id);
        System.out.println(track.getName() + " | " + track.getArtists()[0].getName());
    }

    private Track findTrack(String id) throws IOException, ParseException, SpotifyWebApiException {
        GetTrackRequest getTrackRequest = spotifyApi.getTrack(id).build();
        return getTrackRequest.execute();
    }




    @Override
    public void gerTracksFromAlbum(String id) throws IOException, ParseException, SpotifyWebApiException {
        Paging<TrackSimplified> tracks = findTracksFromAlbum(id);
        for (int i = 0; i < tracks.getItems().length; i++) {
            System.out.println(tracks.getItems()[i].getName() + " | " + tracks.getItems()[0].getArtists()[0].getName());
        }

    }
    private Paging<TrackSimplified> findTracksFromAlbum(String id) throws IOException, ParseException, SpotifyWebApiException {
        GetAlbumsTracksRequest getAlbumsTracksRequest = spotifyApi.getAlbumsTracks(id).build();
        return getAlbumsTracksRequest.execute();
    }






}
