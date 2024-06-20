package ru.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import ru.auth.dto.UserOutDto;
import ru.auth.dto.UserRegistryDto;
import ru.auth.client.UserClient;
import ru.auth.entity.UserEntity;


import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User",description = "Действия с пользователем")
public class UserController {

    private final UserClient userClient;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_OWNER')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Получение списка пользоватлей")
    public List<UserEntity> getUsers() {
        return userClient.getUsers();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Регистрация пользователя")
    public UserEntity createUser(@RequestBody UserRegistryDto userRegistryDto){
        return userClient.createUser(userRegistryDto);
    }

}
