package ru.mp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import ru.mp.dto.item.ItemOutDto;
;
import ru.mp.entity.ItemEntity;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemMapper {

    ItemEntity toEntity(ItemOutDto itemOutDto);

    ItemOutDto toDto(ItemEntity itemEntity);

    List<ItemEntity> toEntityList(List<ItemOutDto> items);

    List<ItemOutDto> toDtoList(List<ItemEntity> items);

    ItemEntity toEn(ItemEntity itemEntity);

}
