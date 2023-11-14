package ru.padwicki.soundseeker.controllersInterfaces;

import org.apache.hc.core5.http.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;

@RequestMapping("/sts")
public interface StSControllerInterface {
    @GetMapping("/convertTrack")
    public void Convert(@RequestParam String id) throws IOException, ParseException, SpotifyWebApiException;
}
