package ru.mp.dto.item;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.serializer.Deserializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemOutDto implements Serializable  {

    private Integer id;

    @Schema(name = "name",description = "Название предмета")
    private String name;

    @Schema(name = "price",description = "Цена предмета")
    private BigDecimal price;

    @Schema(name = "description",description = "Описание предмета")
    private String description;

    @Schema(name = "username",description = "Имя пользователя, купившего предмет")
    private String username;

    @Schema(name = "shopName",description = "Название магазина, в котором покупался предмет")
    private String shopName;

//    @Schema(name = "dealId",description = "Уникальный идентификатор сделки, в которой участвует предмте")
//    private Integer dealId;

    @Schema(name = "comment",description = "Комментарий к предмету")
    private String comment;

    @Schema(name = "createdAt",description = "Время покупки предмета")
    private Instant createdAt = Instant.now();
}
