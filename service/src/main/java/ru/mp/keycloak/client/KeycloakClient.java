package ru.mp.keycloak.client;

public interface KeycloakClient {

    String getToken(String clientId, String clientSecret);
}
