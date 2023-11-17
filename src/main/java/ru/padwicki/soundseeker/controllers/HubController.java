package ru.padwicki.soundseeker.controllers;

import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.padwicki.soundseeker.controllersInterfaces.HubControllerInterface;
import ru.padwicki.soundseeker.service.HubService;
import ru.padwicki.soundseeker.service.StsService;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;


@Controller
public class HubController implements HubControllerInterface {

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
        stsService.initApi(code);
        return "service-to-service/sts";
    }

    @Override
    public String convertPage() {
        return "service-to-service/convert";
    }
}
