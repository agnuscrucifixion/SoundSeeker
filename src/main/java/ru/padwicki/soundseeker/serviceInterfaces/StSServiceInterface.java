package ru.padwicki.soundseeker.serviceInterfaces;

import org.apache.hc.core5.http.ParseException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.io.IOException;
import java.util.List;

public interface StSServiceInterface {
    void convert(String name) throws IOException, ParseException, SpotifyWebApiException;
}

