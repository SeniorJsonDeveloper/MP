package ru.mp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.mp.dto.deal.DealOutDto;
import ru.mp.entity.DealEntity;

import java.util.List;
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DealMapper {

    DealEntity toEntity(DealOutDto dealCreateDto);

    DealOutDto toDto(DealEntity dealEntity);

    List<DealEntity> toEntityList(List<DealOutDto> items);

    List<DealOutDto> toDtoList(List<DealEntity> items);
}
