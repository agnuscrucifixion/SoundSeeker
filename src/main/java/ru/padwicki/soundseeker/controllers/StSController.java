package ru.padwicki.soundseeker.controllers;

import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.padwicki.soundseeker.controllersInterfaces.StSControllerInterface;
import ru.padwicki.soundseeker.pyBase.pyYandex;
import ru.padwicki.soundseeker.service.StsService;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

@RestController
public class StSController implements StSControllerInterface {

    StsService stsService;
    public StSController(StsService stsService) {
        this.stsService = stsService;
    }
    @Override
    public void convertTrack(String id) throws IOException, ParseException, SpotifyWebApiException {
        stsService.getTrack(id);
    }

    @Override
    public void convertTrackFromAlbum(String id) throws IOException, ParseException, SpotifyWebApiException {
        stsService.getTracksFromAlbum(id);
    }

    @Override
    public void getOwnUserPlaylists() throws IOException, ParseException, SpotifyWebApiException {
        stsService.getOwnUserPlaylists();
    }

    @Override
    public void handleSwaggerRedirect(String code) throws IOException, ParseException, SpotifyWebApiException {
        stsService.initApi(code);
    }
}
