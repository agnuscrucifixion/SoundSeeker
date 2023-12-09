package ru.padwicki.soundseeker.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumsTracksRequest;
import se.michaelthelin.spotify.requests.data.library.RemoveUsersSavedTracksRequest;
import se.michaelthelin.spotify.requests.data.library.SaveTracksForUserRequest;
import se.michaelthelin.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;
import org.apache.hc.core5.http.ParseException;
import ru.padwicki.soundseeker.config.auth.SpotifyAuth;
import ru.padwicki.soundseeker.serviceInterfaces.StSServiceInterface;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class StsService implements StSServiceInterface {
    private static final int MAX_TRACKS_PER_REQUEST = 50;
    HubService hubService;
    @Autowired
    public StsService(HubService hubService) {
        this.hubService = hubService;
    }


    @Override
    public void convert(String name) throws IOException, ParseException, SpotifyWebApiException {
        List<Track> listOfTracksForFirstService = hubService.show(name);
        String userId;
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.spotify.com/v1/me"))
                .header("Authorization", "Bearer " + hubService.spotifyApi.getAccessToken())
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
                userId = jsonObject.get("id").getAsString();
                CreatePlaylistRequest createPlaylistRequest = hubService.spotifyApi
                        .createPlaylist(userId,name)
                        .public_(true)
                        .build();
                Playlist playlist = createPlaylistRequest.execute();
                AddItemsToPlaylistRequest addItemsToPlaylistRequest = hubService.spotifyApi
                        .addItemsToPlaylist(playlist.getId(), listOfTracksForFirstService.stream().map(Track::getUri).toArray(String[]::new))
                        .build();
                addItemsToPlaylistRequest.execute();
            } else {
                System.err.println("Request failed with status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void convertLikedSongs() throws IOException, ParseException, SpotifyWebApiException {
        Collections.reverse(hubService.likedSongs);
        for (int i = 0; i < hubService.likedSongs.size(); i += MAX_TRACKS_PER_REQUEST) {
            int toIndex = Math.min(i + MAX_TRACKS_PER_REQUEST, hubService.likedSongs.size());
            List<Track> tracks = hubService.likedSongs.subList(i, toIndex);
            String[] trackIds = tracks.stream()
                    .map(Track::getId)
                    .toArray(String[]::new);
            SaveTracksForUserRequest saveTracksForUserRequest = hubService.spotifyApi.saveTracksForUser(trackIds)
                    .build();
            saveTracksForUserRequest.execute();
        }

    }

    @Override
    public void deleteLikedSongs() throws IOException, ParseException, SpotifyWebApiException {
        for (int i = 0; i < hubService.likedSongs.size(); i += MAX_TRACKS_PER_REQUEST) {
            int toIndex = Math.min(i + MAX_TRACKS_PER_REQUEST, hubService.likedSongs.size());
            List<Track> tracks = hubService.likedSongs.subList(i, toIndex);
            String[] trackIds = tracks.stream()
                    .map(Track::getId)
                    .toArray(String[]::new);
            RemoveUsersSavedTracksRequest removeUsersSavedTracksRequest = hubService.spotifyApi.removeUsersSavedTracks(trackIds)
                    .build();
            removeUsersSavedTracksRequest.execute();
        }

    }
}
