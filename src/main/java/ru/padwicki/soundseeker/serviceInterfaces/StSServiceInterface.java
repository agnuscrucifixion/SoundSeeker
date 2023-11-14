package ru.padwicki.soundseeker.serviceInterfaces;

import org.apache.hc.core5.http.ParseException;
import org.springframework.web.bind.annotation.RequestParam;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;

public interface StSServiceInterface {
    void getTrack(@RequestParam String id) throws IOException, ParseException, SpotifyWebApiException;
}
