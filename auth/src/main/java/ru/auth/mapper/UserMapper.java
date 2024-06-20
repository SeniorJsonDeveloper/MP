package ru.auth.mapper;

import org.mapstruct.*;
import ru.auth.dto.UserOutDto;
import ru.auth.dto.UserRegistryDto;
import ru.auth.entity.UserEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {


    UserEntity toEntity(UserRegistryDto userRegistryDto);

    UserRegistryDto toDto(UserEntity userEntity);

    UserEntity fromDto(UserOutDto user);

    UserOutDto toDtoFromEntity(UserEntity userEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity partialUpdate(UserRegistryDto userRegistryDto, @MappingTarget UserEntity userEntity);
}