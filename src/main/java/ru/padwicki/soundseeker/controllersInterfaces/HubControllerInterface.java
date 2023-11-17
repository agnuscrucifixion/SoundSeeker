package ru.padwicki.soundseeker.controllersInterfaces;

import org.apache.hc.core5.http.ParseException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;

public interface HubControllerInterface {
    @GetMapping("/hub")
    String startPage(Model model) throws IOException, ParseException, SpotifyWebApiException;

    @GetMapping("/callback")
    String handleCallBack(@RequestParam("code") String code, Model model) throws IOException, ParseException, SpotifyWebApiException;

    @GetMapping("/convert")
    String convertPage();
}
