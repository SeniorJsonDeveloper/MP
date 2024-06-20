package ru.mp.keycloak;

import ru.mp.dto.user.UserOutDto;

import java.util.List;

public interface UserClient {

    List<UserOutDto> getUsers();
}
