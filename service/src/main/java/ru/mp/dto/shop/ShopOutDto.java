package ru.mp.dto.shop;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ShopOut",description = "Выходящее дто магазина")
public class ShopOutDto {

    private Integer id;

    @Schema(name = "name",description = "Название магазина")
    private String name;

    @Schema(name ="description",description = "Описание магазина")
    private String description;

    @Schema(name = "deposit",description = "Страховой депозит магазина")
    private Double deposit;
}
