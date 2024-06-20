package ru.mp.dto.deal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "DealOut",description = "Выходящее дто  сделки")
public class DealOutDto implements Serializable {


    @Schema(name = "id",description = "Уникальный идентификатор сделки")
    private Integer id;

    @Schema(name = "shopname",description = "Название магазина")
    private String shopName;

    @Schema(name = "itemName",description = "Название предмета в сделке")
    private String itemName;

    @Schema(name = "itemPrice",description = "Название предмета в сделке")
    private BigDecimal itemPrice;

    @Schema(name = "completed",description = "Статус сделки")
    private Boolean completed;

    @Schema(name = "createdAt",description = "Время создания сделки")
    private Instant createdAt = Instant.now();
}
