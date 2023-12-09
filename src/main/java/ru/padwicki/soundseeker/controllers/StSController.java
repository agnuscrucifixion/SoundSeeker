package ru.padwicki.soundseeker.controllers;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.padwicki.soundseeker.controllersInterfaces.StSControllerInterface;
import ru.padwicki.soundseeker.service.HubService;
import ru.padwicki.soundseeker.service.StsService;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.Objects;

@Controller
public class StSController implements StSControllerInterface {

    StsService stsService;
    HubService hubService;
    @Autowired
    public StSController(StsService stsService, HubService hubService) {
        this.stsService = stsService;
        this.hubService = hubService;
    }


    @Override
    public String spotifyToSpotifyLoginPage(Model model) throws IOException, ParseException, SpotifyWebApiException {
        model.addAttribute("playlists", hubService.getOwnUserPlaylistsSpotify());
        model.addAttribute("likedSongs", hubService.getOwnLibrary());
        model.addAttribute("urlSpotify", hubService.authorization());
        return "spotify-to-spotify/spotify-to-spotify-page-loginSecond";
    }

    @Override
    public String spotifyToSpotify(String name) throws IOException, ParseException, SpotifyWebApiException {
        if (Objects.equals(name, "LikedSongs")) {
            stsService.convertLikedSongs();
        } else {
            stsService.convert(name);

        }
        return "success";
    }

    @Override
    public String deleteLikedSongs() throws IOException, ParseException, SpotifyWebApiException {
        stsService.deleteLikedSongs();
        return "success";
    }


}
