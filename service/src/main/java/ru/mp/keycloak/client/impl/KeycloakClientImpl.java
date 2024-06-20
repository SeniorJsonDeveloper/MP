package ru.mp.keycloak.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.mp.dto.token.TokenOutDto;
import ru.mp.keycloak.client.KeycloakClient;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class KeycloakClientImpl implements KeycloakClient {

    private static final String CLIENT_ID = "client_id";

    private static final String CLIENT_SECRET = "client_secret";

    private static final String CLIENT_CREDENTIALS = "client_credentials";

    private static final String GRANT_TYPE = "grant_type";

    @Value("${app.keycloak.token_endpoint}")
    private String tokenUrl;

    private final RestTemplate restTemplate;

    @Override
    public String getToken(String clientId, String clientSecret) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(CLIENT_ID, clientId);
        params.add(CLIENT_SECRET, clientSecret);
        params.add(GRANT_TYPE, CLIENT_CREDENTIALS);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        var response = restTemplate.postForObject(tokenUrl, request, TokenOutDto.class);
        return Objects.requireNonNull(response).getAccessToken();
    }
}
