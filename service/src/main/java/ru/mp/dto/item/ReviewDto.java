package ru.mp.dto.item;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Review",description = "ДТО с отзывом о товаре")
public class ReviewDto {

    @Schema(name = "comment",description = "Комментарий к товару")
    private String comment;

    @Schema(name = "rating",description = "Оценка товара")
    private Double rating;
}
