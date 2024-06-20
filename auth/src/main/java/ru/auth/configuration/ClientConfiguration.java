package ru.auth.configuration;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KeycloakClientProperties.class)
public class ClientConfiguration {

    @Bean
    public Keycloak keycloak(@Value("${app.keycloak.url}") String keycloakUrl,
                             @Value("${app.keycloak.realm}") String realm,
                             KeycloakClientProperties keycloakClientProperties) {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakUrl)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(keycloakClientProperties.getId())
                .clientSecret(keycloakClientProperties.getSecret())
                .build();
    }
}
