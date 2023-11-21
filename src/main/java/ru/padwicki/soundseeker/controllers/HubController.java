package ru.padwicki.soundseeker.controllers;

import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.padwicki.soundseeker.controllersInterfaces.HubControllerInterface;
import ru.padwicki.soundseeker.service.HubService;
import ru.padwicki.soundseeker.service.StsService;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;


@Controller
public class HubController implements HubControllerInterface {

    private int count = 0;
    HubService hubService;
    StsService stsService;
    public HubController(HubService hubService, StsService stsService) {
        this.hubService = hubService;
        this.stsService = stsService;
    }

    @Override
    public String startPage(Model model){
        if (!model.containsAttribute("urlSpotify")) {
            model.addAttribute("urlSpotify", hubService.authorization());
        }
        return "hub";
    }

    @Override
    public String handleCallBack(String code, Model model) throws IOException, ParseException, SpotifyWebApiException {
        if (count == 1) {
            model.addAttribute("playlists", hubService.getOwnUserPlaylistsSpotify());
        }
        count++;
        if(count == 1) {
            hubService.initApi(code);
            return "sts";
        }
        hubService.initApi(code);
        count = 0;
        return "spotify-to-spotify/spotify-to-spotify-page";
    }


    @Override
    public String convertPage() {
        return "convert";
    }

    @Override
    public String show(String name, Model model) throws IOException, ParseException, SpotifyWebApiException {
        model.addAttribute("playlist", hubService.show(name));
        return "spotify-to-spotify/show";
    }

    @Override
    public void returnNoFavicon() {}

}
