package ru.mp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mp.configuration.cache.CacheProperties;
import ru.mp.dto.deal.DealCreateDto;
import ru.mp.dto.deal.DealOutDto;

import ru.mp.entity.DealEntity;
import ru.mp.entity.ItemEntity;
import ru.mp.entity.ShopEntity;
import ru.mp.exception.NotFoundException;



import ru.mp.mapper.DealMapper;
import ru.mp.mapper.ItemMapper;
import ru.mp.mapper.ShopMapper;
import ru.mp.repository.DealRepository;

import ru.mp.service.DealService;
import ru.mp.service.ItemService;
import ru.mp.service.ShopService;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheManager = "redisCacheManager")
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;

    private final ItemMapper itemMapper;

    private final ShopService shopService;

    private final ItemService itemService;

    private final DealMapper dealMapper;

    private final ShopMapper shopMapper;

//    private final AtomicLong count = new AtomicLong(0);

    @Override
    @Cacheable(cacheNames = CacheProperties.CacheNames.DEAL_BY_ID,key = "#id")
    public DealOutDto findById(Integer id) {
        return dealMapper.toDto(dealRepository.findById(id)
                .orElseThrow(()->new NotFoundException
                        (MessageFormat.format("Deal with id {0} not found",id))));
    }

    @Override
    public List<DealOutDto> findAllDeals() {
        return dealMapper.toDtoList(dealRepository.findAll());
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheProperties.CacheNames.NEW_ITEM)
    public DealOutDto createDeal(DealCreateDto dealCreateDto) {
        DealEntity deal = new DealEntity();
        deal.setId(deal.getId());
        deal.setCreatedAt(Instant.now());
        List<ShopEntity> shops = deal.getShop()
                .stream() // извлечение и добавления магазина в список магазинов в сделке
                                .map(it->ShopEntity.builder()
                                        .id(dealCreateDto.getShopId())
                                        .build())
                                        .toList();
        deal.setShop(shops);
        List<ItemEntity> items = deal.getItems()
                .stream()
                .map(it->ItemEntity.builder()
                        .id(dealCreateDto.getItemId())
                        .build())
                .toList();
        deal.setItems(items);
        deal.setCompleted(true);
        if (dealCreateDto.getShopId() == null && dealCreateDto.getItemId() == null) {
            deal.setCompleted(false);
        }
        dealRepository.save(deal);
        DealOutDto dto = new DealOutDto();
        ItemEntity itemEntity = itemMapper.toEntity(itemService.findById(dealCreateDto.getItemId()));
        String itemNaming = itemEntity.getName();
        BigDecimal itemPrice = itemEntity.getPrice();
        String shopName = String.valueOf(shops.stream().findAny().orElseThrow());
        dto.setId(deal.getId());
        dto.setItemName(itemNaming);
        dto.setShopName(shopName);;
        dto.setCreatedAt(deal.getCreatedAt());
        dto.setItemPrice(itemPrice);
        dto.setCompleted(true);
        return dto;
    }

    @Override
    @CachePut(cacheNames = CacheProperties.CacheNames.DEAL_BY_ID,key = "#id")
    @Transactional
    public void editDeal(Integer id, DealCreateDto dealCreateDto) {
         dealRepository.findById(id)
                .ifPresentOrElse(o->{
                    o.setId(o.getId());
                    o.setCreatedAt(Instant.now());
                    List<ShopEntity> shops = getShopsFromList(dealCreateDto);
                    o.setShop(shops);
                    List<ItemEntity> items = getItemsFromList(dealCreateDto);
                    o.setItems(items);
                    o.setCompleted(true);
                },()->{
                    throw new NotFoundException(MessageFormat.format("Deal with id {0} not found",id));
                });
    }

    @Override
    @Cacheable(cacheNames = CacheProperties.CacheNames.DEAL_BY_ID,key = "#id")
    public void deleteDeal(Integer id) {
        DealEntity deal = dealMapper.toEntity(findById(id));
        if (deal == null) {
            throw new NotFoundException(MessageFormat.format("Deal with id {0} not found",id));
        }

        dealRepository.deleteAllById(deal.getItems().stream()
                .map(ItemEntity::getId)
                .collect(Collectors.toList()));

        dealRepository.delete(deal);


    }

    private static List<ItemEntity> getItemsFromList(DealCreateDto dealCreateDto) {
        DealEntity deal = new DealEntity();
        List<ItemEntity> result = deal.getItems().stream()
                .map(it->ItemEntity.builder()
                        .id(dealCreateDto.getItemId())
                        .build())
                .toList();
        deal.setItems(result);
        return result;
    }

    private static List<ShopEntity> getShopsFromList(DealCreateDto dealCreateDto) {
        DealEntity deal = new DealEntity();
        List<ShopEntity> shops = deal.getShop()
                .stream()
                .map(o->ShopEntity.builder()
                        .id(dealCreateDto.getShopId())
                        .build())
                .toList();
        deal.setShop(shops);
        return shops;
    }


}
