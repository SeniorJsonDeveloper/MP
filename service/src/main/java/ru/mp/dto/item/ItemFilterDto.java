package ru.mp.dto.item;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "itemFilter",description = "ДТО предмета с фильтрацией")
public class ItemFilterDto {

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

    @Schema(name = "minPrice",description = "Минимальная цена предмета")
    private BigDecimal minPrice;

    @Schema(name = "maxPrice",description = "Максимальная цена предмета")
    private BigDecimal maxPrice;

    @Schema(name = "category",description = "Категория предмета")
    private String category;

    public PageRequest toPageRequest() {
        return PageRequest.of(pageNumber, pageSize);
    }

}
