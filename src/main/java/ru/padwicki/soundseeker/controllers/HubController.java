package ru.padwicki.soundseeker.controllers;

import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.padwicki.soundseeker.controllersInterfaces.HubControllerInterface;
import ru.padwicki.soundseeker.service.HubService;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import java.io.IOException;


@Controller
public class HubController implements HubControllerInterface {

    HubService hubService;

    public HubController(HubService hubService) {
        this.hubService = hubService;
    }

    @Override
    public String startPage(Model model) throws IOException, ParseException, SpotifyWebApiException {
        model.addAttribute("urlSpotify", hubService.authorization());
        return "hub";
    }
}
