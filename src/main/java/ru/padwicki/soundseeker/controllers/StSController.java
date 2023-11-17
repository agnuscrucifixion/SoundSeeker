package ru.padwicki.soundseeker.controllers;

import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.padwicki.soundseeker.controllersInterfaces.StSControllerInterface;
import ru.padwicki.soundseeker.service.StsService;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;

@Controller
public class StSController implements StSControllerInterface {

    StsService stsService;
    public StSController(StsService stsService) {
        this.stsService = stsService;
    }


    @Override
    public String spotifyToSpotify(Model model) throws IOException, ParseException, SpotifyWebApiException {
        model.addAttribute("playlists", stsService.getOwnUserPlaylists());
        return "service-to-service/spotify-to-spotify";
    }

    @Override
    public String show(String name, Model model) throws IOException, ParseException, SpotifyWebApiException {
        model.addAttribute("playlist", stsService.show(name));
        return "service-to-service/show";
    }
}
