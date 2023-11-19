package ru.padwicki.soundseeker.controllersInterfaces;

import org.apache.hc.core5.http.ParseException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

@RequestMapping("/sts")
public interface StSControllerInterface {

    @GetMapping("/spotify-to-spotify-login-page")
    String spotifyToSpotifyLoginPage(Model model) throws IOException, ParseException, SpotifyWebApiException;
    @PostMapping("/spotify-to-spotify")
    String spotifyToSpotify(@ModelAttribute("name") String name) throws IOException, ParseException, SpotifyWebApiException;




    @GetMapping("/{name}")
    String show(@PathVariable("name") String name, Model model) throws IOException, ParseException, SpotifyWebApiException;
}
