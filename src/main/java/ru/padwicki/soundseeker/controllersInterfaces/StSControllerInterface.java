package ru.padwicki.soundseeker.controllersInterfaces;

import org.apache.hc.core5.http.ParseException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

@RequestMapping("/sts")
public interface StSControllerInterface {

    @GetMapping("/spotify-to-spotify")
    String spotifyToSpotify(Model model) throws IOException, ParseException, SpotifyWebApiException;




    @GetMapping("/{name}")
    String show(@PathVariable("name") String name, Model model) throws IOException, ParseException, SpotifyWebApiException;
}
