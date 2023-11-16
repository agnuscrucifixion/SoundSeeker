package ru.padwicki.soundseeker.serviceInterfaces;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.net.URI;

public interface HubServiceInterface {
    public URI authorization() throws IOException, ParseException, SpotifyWebApiException;
}
