package ru.padwicki.soundseeker.config.auth;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;

@Component
@PropertySource("classpath:application.properties")
public class SpotifyAuth implements InitializingBean {
    @Value("${spotify.client_id}")
    private String clientId;
    @Value("${spotify.client_secret}")
    private String clientSecret;
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/swagger-ui/index.html#/");
    private AuthorizationCodeUriRequest authorizationCodeUriRequest;

    @Override
    public void afterPropertiesSet() {
       final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirectUri)
                .build();
        authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .build();
    }
    public URI authorization() {
        return authorizationCodeUriRequest.execute();
    }
}
