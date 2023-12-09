package ru.padwicki.soundseeker.config.auth;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Set;

@Component("SpotifyAuthURL")
@PropertySource("classpath:application.properties")
public class SpotifyAuth {
    @Value("${spotify.client_id}")
    private String clientId;
    @Value("${spotify.client_secret}")
    private String clientSecret;
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/callback");
    private AuthorizationCodeUriRequest authorizationCodeUriRequest;
    private URI url;
    private SpotifyApi spotifyApi;
    Set<String> scopes = Set.of("user-library-modify","user-library-read","playlist-read-private", "playlist-modify-public", "playlist-modify-private");

    public SpotifyApi clientCredentials(String codeLocal) throws IOException, ParseException, SpotifyWebApiException {
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(codeLocal)
                .build();
        final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
        String accessToken = authorizationCodeCredentials.getAccessToken();
        spotifyApi.setAccessToken(accessToken);
        String refreshToken = authorizationCodeCredentials.getRefreshToken();
        spotifyApi.setRefreshToken(refreshToken);
        return spotifyApi;
    }

    public URI authorization() {
        spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirectUri)
                .build();
        authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .scope(String.join(" ", scopes))
                .show_dialog(true)
                .build();
        url = authorizationCodeUriRequest.execute();
        return url;
    }
}