package ru.auth.client;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.auth.dto.UserOutDto;
import ru.auth.dto.UserRegistryDto;
import ru.auth.entity.UserEntity;
import ru.auth.exception.AlreadyExistsException;
import ru.auth.mapper.UserMapper;
import ru.auth.repository.UserRepository;


import java.util.List;

@Component
@RequiredArgsConstructor
public class UserClientImpl implements UserClient {

    private static final String ROLE_USER = "ROLE_USER";

    private final Keycloak keycloak;

    @Value("${app.keycloak.realm}")
    private String realm;

    private final UserRepository userRepository;

    @Override
    public UserEntity createUser(UserRegistryDto userRegistryDto) {

        RealmResource resource = keycloak.realm(realm);
        UserRepresentation userRepresentation = createUserRepresentation(userRegistryDto);

        setCredentials(userRepresentation,userRegistryDto.getPassword());
        var response = resource.users().create(userRepresentation);

        String userId = CreatedResponseUtil.getCreatedId(response);

        addRole(resource, userId);

        return new UserEntity(userId,userRegistryDto.getUsername(),userRegistryDto.getEmail());
    }




    @Override
    public List<UserEntity> getUsers() {
        RealmResource resource = keycloak.realm(realm);
        return resource.users().list().stream().map(o->new UserEntity(o.getId(),o.getUsername(),o.getEmail()
                )).toList();
    }

    public UserRepresentation createUserRepresentation(UserRegistryDto userRegistryDto) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(userRegistryDto.getUsername());
        if (userRepository.existsByUsername(userRegistryDto.getUsername())){
            throw new AlreadyExistsException("Username already exists, please change your username!");
        }
        userRepresentation.setEmail(userRegistryDto.getEmail());
        if (userRepository.existsByEmail(userRegistryDto.getEmail())){
            throw new AlreadyExistsException("Email already exists, please change your email!");
        }
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);
        return userRepresentation;
    }

    private void setCredentials(UserRepresentation userRepresentation, String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        userRepresentation.setCredentials(List.of(credentialRepresentation));
        userRepresentation.setRealmRoles(List.of(ROLE_USER));
    }

    private void addRole(RealmResource resource, String userId) {
        UserResource userResource = resource.users().get(userId);
        var role = keycloak.realm(realm).roles().get(ROLE_USER).toRepresentation();
        userResource.roles().realmLevel().add(List.of(role));
    }
}
