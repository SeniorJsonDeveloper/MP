package ru.mp.dto.shop;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ShopInput",description = "Входящее дто при создании магазина")
public class ShopInputDto {

    @Schema(name = "name",description = "Название магазина")
    private String name;

    @Schema(name ="description",description = "Описание магазина")
    private String description;

    @Schema(name = "deposit",description = "Страховой депозит магазина")
    @Min(value = 100000,message = "Страховой депозит не может быть меньше 100000")
    private Double deposit;
}
