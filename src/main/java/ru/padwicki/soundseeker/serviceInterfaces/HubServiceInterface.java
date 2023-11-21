package ru.padwicki.soundseeker.serviceInterfaces;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.io.IOException;
import java.net.URI;
import java.util.List;

public interface HubServiceInterface {
    URI authorization() throws IOException, ParseException, SpotifyWebApiException;
    List<Track> show(String name) throws IOException, ParseException, SpotifyWebApiException;
}
