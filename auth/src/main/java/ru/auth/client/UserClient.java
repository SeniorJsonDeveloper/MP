package ru.auth.client;




import ru.auth.dto.UserOutDto;
import ru.auth.dto.UserRegistryDto;
import ru.auth.entity.UserEntity;

import java.util.List;

public interface UserClient {

    UserEntity createUser(UserRegistryDto userRegistryDto);

    List<UserEntity> getUsers();
}
