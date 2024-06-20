package ru.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UserRegistry",description = "Входящее ДТО пользователя при регистрации")
public class UserRegistryDto {

    @Schema(name = "username",description = "Никнейм пользователя")
    private String username;

    @Schema(name = "password",description = "Пароль пользователя")
    private String password;

    @Schema(name = "email",description = "Почта пользователя")
    private String email;
}
