package ru.padwicki.soundseeker.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import ru.padwicki.soundseeker.config.auth.CredentialsClient;
import ru.padwicki.soundseeker.config.auth.SpotifyAuth;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumsTracksRequest;
import se.michaelthelin.spotify.requests.data.player.GetUsersCurrentlyPlayingTrackRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;
import org.apache.hc.core5.http.ParseException;
import ru.padwicki.soundseeker.serviceInterfaces.StSServiceInterface;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

@Service
public class StsService implements StSServiceInterface {
    SpotifyAuth spotifyAuth;
    @Autowired
    public StsService(SpotifyAuth spotifyAuth) {
        this.spotifyAuth = spotifyAuth;
    }
    private SpotifyApi spotifyApi;

    public void initApi(String code) throws IOException, ParseException, SpotifyWebApiException {
        String[] data = spotifyAuth.clientCredentials(code);
        spotifyApi = new SpotifyApi.Builder()
                .setClientId(data[0])
                .setClientSecret(data[1])
                .setRedirectUri(URI.create(data[2]))
                .build();
        spotifyApi.authorizationCode(data[3]);
        spotifyApi.setAccessToken(data[4]);
        spotifyApi.setRefreshToken(data[5]);
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
    public void getTracksFromAlbum(String id) throws IOException, ParseException, SpotifyWebApiException {
        Paging<TrackSimplified> tracks = findTracksFromAlbum(id);
        for (int i = 0; i < tracks.getItems().length; i++) {
            System.out.println(tracks.getItems()[i].getName() + " | " + tracks.getItems()[0].getArtists()[0].getName());
        }

    }

    private Paging<TrackSimplified> findTracksFromAlbum(String id) throws IOException, ParseException, SpotifyWebApiException {
        GetAlbumsTracksRequest getAlbumsTracksRequest = spotifyApi.getAlbumsTracks(id).build();
        return getAlbumsTracksRequest.execute();
    }


    @Override
    public void getOwnUserPlaylists() throws IOException, ParseException, SpotifyWebApiException {
        GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = spotifyApi
                .getListOfCurrentUsersPlaylists()
                .build();
        Paging<PlaylistSimplified> playlists = getListOfCurrentUsersPlaylistsRequest.execute();
        for (int i = 0; i < playlists.getItems().length; i++) {
            System.out.println(playlists.getItems()[i].getName() + "\n" + playlists.getItems()[i].getTracks().getTotal());
        }

    }



}
