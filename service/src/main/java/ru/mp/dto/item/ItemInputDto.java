package ru.mp.dto.item;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ItemInput",description = "Входящее дто при создании предмета")
public class ItemInputDto {

    @Schema(name = "name",description = "Название предмета")
    private String name;

    @Schema(name = "description",description = "Описание предмета")
    private String description;

    @Schema(name = "price",description = "Цена предмета")
    private BigDecimal price;

    @Schema(name = "category",description = "Категория предмета")
    private String category;

    @Schema(name = "userId",description = "Уникальный идентификатор пользователя, покупающего предмет")
    @Nullable
    private Integer userId;

    @Schema(name = "shopId",description = "Уникальный идентификатор магазина , в котором находится предмет")
    @Nullable
    private Integer shopId;

}
