package ru.mp.dto.shop;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ShopFilter",description = "ДТО магазина с фильтрацией")
public class ShopFilterDto {

    @Schema(name = "pageSize",description = "Количество умешающихся предметов на странице")
    @NotNull
    @Positive
    @Builder.Default
    private Integer pageSize = 10;

    @Schema(name = "pageNumber",description = "Номер страницы")
    @NotNull
    @PositiveOrZero
    @Builder.Default
    private Integer pageNumber = 0;

    @Schema(name = "minRating",description = "Минимальный рейтинг магазина")
    private Double minRating;

    @Schema(name = "maxRating",description = "Максимальный рейтинг магазина")
    private Double maxRating;

    public PageRequest toPageRequest() {
        return PageRequest.of(pageNumber, pageSize);
    }

}
