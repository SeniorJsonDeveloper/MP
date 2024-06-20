package ru.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UserOut",description = "Выходящее ДТО пользователя")
public class UserOutDto {

    @Schema(name = "id",description = "Уникальный идентификатор пользователя")
    private String id;

    @Schema(name = "username",description = "Никнейм пользователя")
    private String username;

    @Schema(name = "email",description = "Почта пользователя")
    private String email;


}
