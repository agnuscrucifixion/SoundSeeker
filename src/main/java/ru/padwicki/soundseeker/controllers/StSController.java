package ru.padwicki.soundseeker.controllers;

import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.padwicki.soundseeker.controllersInterfaces.StSControllerInterface;
import ru.padwicki.soundseeker.pyBase.pyYandex;
import ru.padwicki.soundseeker.service.StsService;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;

@RestController
public class StSController implements StSControllerInterface {

    StsService stsService;
    public StSController(StsService stsService) {
        this.stsService = stsService;
    }
    @Override
    public void Convert(String id) throws IOException, ParseException, SpotifyWebApiException {
        stsService.getTrack(id);
    }
}
