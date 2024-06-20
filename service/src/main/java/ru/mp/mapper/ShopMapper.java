package ru.mp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.mp.dto.shop.ShopInputDto;
import ru.mp.dto.shop.ShopOutDto;
import ru.mp.entity.ShopEntity;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShopMapper {

    ShopEntity toEntity(ShopOutDto shopInputDto);

    ShopOutDto toDto(ShopEntity shopEntity);

    List<ShopEntity> toEntityList(List<ShopInputDto> items);

    List<ShopOutDto> toDtoList(List<ShopEntity> items);
}
