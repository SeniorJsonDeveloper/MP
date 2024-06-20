package ru.mp.keycloak;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.mp.dto.user.UserOutDto;
import ru.mp.keycloak.client.KeycloakClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserClientImpl implements UserClient {

    @Value("${app.integration.user-app.users-url}")
    private String userUrl;

    private final KeycloakClient keycloakClient;

    private final KeycloakCredentials keycloakCredentials;

    private final RestTemplate restTemplate;

    @Override
    public List<UserOutDto> getUsers() {
       String token = keycloakClient.getToken(keycloakCredentials.getId(),keycloakCredentials.getSecret());
       HttpHeaders headers = new HttpHeaders();
       headers.setBearerAuth(token);

       HttpEntity<?> entity = new HttpEntity<>(headers);

       var response = restTemplate.exchange(
               userUrl,
               HttpMethod.GET,
               entity,
               new ParameterizedTypeReference<List<UserOutDto>>() {
               }
       );

       return response.getBody();
    }
}
