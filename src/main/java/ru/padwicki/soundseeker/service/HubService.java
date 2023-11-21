package ru.padwicki.soundseeker.service;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.padwicki.soundseeker.config.auth.SpotifyAuth;
import ru.padwicki.soundseeker.serviceInterfaces.HubServiceInterface;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumsTracksRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;

import java.io.IOException;
import java.net.URI;
import java.util.*;

@Service
public class HubService implements HubServiceInterface {
    SpotifyAuth spotifyAuth;
    SpotifyApi spotifyApi;
    List<PlaylistSimplified> playlists;
    @Autowired
    public HubService(SpotifyAuth spotifyAuth) {
        this.spotifyAuth = spotifyAuth;
    }
    @Override
    public URI authorization(){
        return spotifyAuth.authorization();
    }

    public void initApi(String code) throws IOException, ParseException, SpotifyWebApiException {
        spotifyApi = spotifyAuth.clientCredentials(code);
    }

    public void getTrack(String id) throws IOException, ParseException, SpotifyWebApiException {
        Track track = findTrack(id);
        System.out.println(track.getName() + " | " + track.getArtists()[0].getName());
    }

    private Track findTrack(String id) throws IOException, ParseException, SpotifyWebApiException {
        GetTrackRequest getTrackRequest = spotifyApi.getTrack(id).build();
        return getTrackRequest.execute();
    }

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
    public List<PlaylistSimplified> getOwnUserPlaylistsSpotify() throws IOException, ParseException, SpotifyWebApiException {
        GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = spotifyApi
                .getListOfCurrentUsersPlaylists()
                .build();
        playlists = Arrays.stream(getListOfCurrentUsersPlaylistsRequest.execute().getItems()).filter(track -> !Objects.equals(track.getOwner().getDisplayName(), "Spotify")).toList();

        return playlists;
    }

    @Override
    public List<Track> show(String name) throws IOException, ParseException, SpotifyWebApiException {
        GetPlaylistsItemsRequest getPlaylistsItemsRequest;
        Paging<PlaylistTrack> list;
        List<PlaylistTrack> tracks = new ArrayList<>();
        List<Track> allTracks = new ArrayList<>();
        Optional<PlaylistSimplified> playlist = playlists
                .stream()
                .filter(track -> Objects.equals(track.getName(), name))
                .findAny();
        if (playlist.isPresent()) {
            int offset = 0;
            int limit = 100;
            getPlaylistsItemsRequest = spotifyApi
                    .getPlaylistsItems(playlist.get().getId())
                    .offset(offset)
                    .limit(limit)
                    .build();
            do {
                list = getPlaylistsItemsRequest.execute();
                List<PlaylistTrack> items = Arrays.asList(list.getItems());
                tracks.addAll(items);
                offset += limit;
            } while (offset < list.getTotal());
            allTracks = tracks.stream().map(track -> (Track) track.getTrack()).toList();
        }
        return allTracks;
    }
}
