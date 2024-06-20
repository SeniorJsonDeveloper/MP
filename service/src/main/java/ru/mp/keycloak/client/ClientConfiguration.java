package ru.mp.keycloak.client;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ru.mp.keycloak.KeycloakCredentials;

@Configuration
@EnableConfigurationProperties(KeycloakCredentials.class)
@Component
public class ClientConfiguration {

    @Bean
    public Keycloak keycloak(@Value("${app.keycloak.url}") String url,
                             @Value("${app.keycloak.realm}") String realm,
                             KeycloakCredentials keycloakCredentials) {
        return KeycloakBuilder.builder()
                .serverUrl(url)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(keycloakCredentials.getId())
                .clientSecret(keycloakCredentials.getSecret())
                .build();
    }
}
