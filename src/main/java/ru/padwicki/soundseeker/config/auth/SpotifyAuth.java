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

@Component("SpotifyAuthURL")
@PropertySource("classpath:application.properties")
public class SpotifyAuth implements InitializingBean {
    @Value("${spotify.client_id}")
    private String clientId;
    @Value("${spotify.client_secret}")
    private String clientSecret;
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/callback");
    private AuthorizationCodeUriRequest authorizationCodeUriRequest;
    private AuthorizationCodeRequest authorizationCodeRequest;
    private URI url;
    private SpotifyApi spotifyApi;
    private String accessToken;
    private String refreshToken;

    public String[] clientCredentials(String codeLocal) throws IOException, ParseException, SpotifyWebApiException {
        authorizationCodeRequest = spotifyApi.authorizationCode(codeLocal)
                .build();
        final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
        accessToken = authorizationCodeCredentials.getAccessToken();
        spotifyApi.setAccessToken(accessToken);
        refreshToken = authorizationCodeCredentials.getRefreshToken();
        spotifyApi.setRefreshToken(refreshToken);
        return new String[]{clientId, clientSecret, redirectUri.toString(), codeLocal, accessToken, refreshToken};
    }
    @Override
    public void afterPropertiesSet() {
        spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirectUri)
                .build();
        authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .build();
    }
    public URI authorization() {
        if (url == null) {
            url = authorizationCodeUriRequest.execute();
        }
        return url;
    }
}
