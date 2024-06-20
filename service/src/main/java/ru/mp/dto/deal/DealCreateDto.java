package ru.mp.dto.deal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "DealCreate",description = "Входящее дто при создании сделки")
public class DealCreateDto implements Serializable {

    @Schema(name = "shopId",description = "Уникальный идентификатор магазина")
    private Integer shopId;

    @Schema(name = "itemId",description = "Уникальный идентифкатор предмета")
    private Integer itemId;
}
