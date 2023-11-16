package ru.padwicki.soundseeker.controllersInterfaces;

import org.apache.hc.core5.http.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

@RequestMapping("/sts")
public interface StSControllerInterface {
    @GetMapping("/convertTrack")
    void convertTrack(@RequestParam String id) throws IOException, ParseException, SpotifyWebApiException;

    @GetMapping("/convertTrackFromAlbum")
    void convertTrackFromAlbum(@RequestParam String id) throws IOException, ParseException, SpotifyWebApiException;

    @GetMapping("/getOwnUserPlaylists")
    void getOwnUserPlaylists() throws IOException, ParseException, SpotifyWebApiException;

    @GetMapping("/handle")
    void handleSwaggerRedirect(@RequestParam("code") String code) throws IOException, ParseException, SpotifyWebApiException;
}
