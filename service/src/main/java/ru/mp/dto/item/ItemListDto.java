package ru.mp.dto.item;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ItemList",description = "ДТО со списком предметов")
public class ItemListDto {

    private List<ItemOutDto> items = new ArrayList<>();

}
