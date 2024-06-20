package ru.mp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mp.dto.shop.ShopFilterDto;
import ru.mp.dto.shop.ShopInputDto;
import ru.mp.dto.shop.ShopOutDto;
import ru.mp.entity.ItemEntity;
import ru.mp.entity.ShopEntity;
import ru.mp.exception.AlreadyExistsException;
import ru.mp.exception.NotFoundException;
import ru.mp.mapper.ShopMapper;
import ru.mp.repository.ShopRepository;
import ru.mp.repository.specification.ShopSpecification;
import ru.mp.service.ItemService;
import ru.mp.service.ShopService;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    private final ShopMapper shopMapper;

    @Lazy
    @Setter
    private ItemService itemService;


    @Override
    public List<ShopOutDto> getAllShops() {
        return shopMapper.toDtoList(shopRepository.findAll());
    }

    @Override
    public List<ShopOutDto> getShopWithSort(ShopFilterDto shopFilterDto) {
        return shopMapper.toDtoList(shopRepository.findAll(
                ShopSpecification.withSorting(shopFilterDto),
                PageRequest.of(shopFilterDto.getPageNumber(),shopFilterDto.getPageSize()
        )).getContent());
    }

    @Override
    public ShopOutDto findByName(String name) {
        return shopMapper.toDto(shopRepository.findByName(name)
                .orElseThrow(()-> new NotFoundException
                        (MessageFormat.format("Shop with name {0} not found",name))));
    }

    @Override
    public ShopOutDto getShopById(Integer id) {
        return shopMapper.toDto(shopRepository.findById(id)
                .orElseThrow(()->new NotFoundException(
                        MessageFormat.format("Shop with id {0} not found", id)
                )));
    }

    @Override
    @Transactional
    public void registerShop(ShopInputDto shopInputDto) {
        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setName(shopInputDto.getName());
        if (shopRepository.existsByName(shopInputDto.getName())) {
            throw new AlreadyExistsException("Shop with name already exists, please choose another name!");
        }
        shopEntity.setDescription(shopInputDto.getDescription());
        shopEntity.setDeposit(shopInputDto.getDeposit());
        shopRepository.save(shopEntity);

    }

    @Override
    @Transactional
    public void updateShop(Integer id, ShopInputDto shopInputDto) {
         shopRepository.findById(id)
                .ifPresentOrElse(m->{
                    m.setName(shopInputDto.getName());
                    m.setDescription(shopInputDto.getDescription());
                    m.setDeposit(shopInputDto.getDeposit());
                },()->{
                    throw new NotFoundException(MessageFormat.format("Shop with id {0} not found", id));
                });
    }

    @Override
    public void deleteShop(Integer id) {
        ShopEntity shop = shopMapper.toEntity(getShopById(id));

        if (shop == null){
            throw new NotFoundException(MessageFormat.format("Shop with id {0} not found", id));
        }

        itemService.deleteAllItems(shop.getItems().stream()
                .map(ItemEntity::getId)
                .collect(Collectors.toList()));

        shopRepository.delete(shop);

    }
}
