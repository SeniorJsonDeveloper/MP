package ru.mp.service.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mp.configuration.cache.CacheProperties;
import ru.mp.dto.item.ItemFilterDto;
import ru.mp.dto.item.ItemInputDto;
import ru.mp.dto.item.ItemOutDto;
import ru.mp.dto.item.ReviewDto;
import ru.mp.entity.ItemEntity;
import ru.mp.entity.ShopEntity;
import ru.mp.exception.BadRequestException;
import ru.mp.exception.NotFoundException;

import ru.mp.mapper.ItemMapper;
import ru.mp.mapper.ShopMapper;
import ru.mp.repository.ItemRepository;
import ru.mp.repository.specification.ItemSpecification;

import ru.mp.service.DealService;
import ru.mp.service.ItemService;
import ru.mp.service.ShopService;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheManager = "redisCacheManager")
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final ShopService shopService;

    private final ItemMapper itemMapper;

    private final ShopMapper shopMapper;

    private final AtomicInteger counter = new AtomicInteger(1);


    @Override
    @CachePut(cacheNames = CacheProperties.CacheNames.ITEM_BY_ID,key = "#id")
    public ItemOutDto findById(Integer id) {
        return itemMapper.toDto(itemRepository.findById(id)
                .orElseThrow(()-> new NotFoundException
                        (MessageFormat.format("Item with id {0} not found}",id))));
    }

    @Override
    public List<ItemOutDto> findAll() {
        return itemMapper.toDtoList(itemRepository.findAll());
    }

    @Override
    @Cacheable(cacheNames = CacheProperties.CacheNames.ITEM_BY_FILTER)
    public List<ItemOutDto> findWithSorting(ItemFilterDto itemFilterDto) {
        return itemMapper.toDtoList(
                itemRepository.findAll(ItemSpecification.withSorting(itemFilterDto),
                        PageRequest.of(itemFilterDto.getPageNumber(),
                                itemFilterDto.getPageSize())).getContent()
        );
    }

    @Override
    @Cacheable(cacheNames = CacheProperties.CacheNames.ITEM_BY_NAME)
    public ItemOutDto findByName(String name) {
        return itemMapper.toDto(itemRepository.findByName(name)
                .orElseThrow(()->new NotFoundException
                        (MessageFormat.format("Item with name {0} not found",name))));
    }

    @Override
    @Transactional
    @Cacheable(cacheNames = CacheProperties.CacheNames.ITEM_BY_ID,key = "#id")
    public void deleteItemById(Integer id) {
        ItemEntity item = itemMapper.toEntity(findById(id));
        if (item == null){
            throw new NotFoundException(MessageFormat.format("Item with id {0} not found",id));
        }
        itemRepository.deleteById(id);

    }

    @Override
    @CachePut(cacheNames = CacheProperties.CacheNames.ITEM_BY_ID,key = "#id")
    public void addComment(String comment, Integer itemId) {
        ItemEntity item = itemMapper.toEntity(findById(itemId));
        if (itemRepository.existsByComment(comment)){
            throw new BadRequestException(MessageFormat.format("Comment {0} already exists",comment));
        }
        item.setComment(comment);
        itemRepository.save(item);

    }

    @Override
    @Transactional
    @Cacheable(cacheNames = CacheProperties.CacheNames.NEW_ITEM)
    public void saveItem(ItemInputDto itemInputDto) {
        ItemEntity item = new ItemEntity();
        item.setName(itemInputDto.getName());
        item.setPrice(itemInputDto.getPrice());
        item.setCategory(itemInputDto.getCategory());
        item.setDescription(itemInputDto.getDescription());
        item.setQuantity(counter.getAndIncrement());
        ShopEntity shop = shopMapper.toEntity(shopService.getShopById(itemInputDto.getShopId()));
        item.setShops(shop);
        itemMapper.toDto(itemRepository.save(item));


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(cacheNames = CacheProperties.CacheNames.ITEM_BY_ID,key = "#id")
    public void updateItem(Integer id, ItemInputDto itemInputDto) {
        itemRepository.findById(id)
                .ifPresentOrElse(o->{
                    o.setName(itemInputDto.getName());
                    o.setDescription(itemInputDto.getDescription());
                    o.setPrice(itemInputDto.getPrice());
                    o.setQuantity(counter.getAndIncrement());
                    o.setCategory(itemInputDto.getCategory());
                    o.setShops(shopMapper.toEntity(shopService.getShopById(itemInputDto.getShopId())));
                },()->{
                    throw new NotFoundException(MessageFormat.format("Item with id {0} not found",id));
                });

    }

    @Override
    public void deleteAllItems(List<Integer> ids) {
        itemRepository.deleteAllById(ids);
    }

    @Override
    public void makeReview(Integer itemId, ReviewDto reviewDto) {
        ItemEntity item = itemMapper.toEntity(findById(itemId));
//        if (itemRepository.existsById(itemId)){
//            throw new BadRequestException(MessageFormat.format("Review {0} already added",reviewDto.getComment()));
//        } //TODO: Подумать над элементами валидации
        item.setComment(reviewDto.getComment());
        item.setRating(reviewDto.getRating());
        calculationRating(reviewDto.getComment(), reviewDto.getRating(), itemId);
        itemRepository.save(item);

    }


    private void calculationRating(String comment, Double rating, Integer itemId){
        if (rating!=null && rating>0) {
            ItemEntity item = itemMapper.toEntity(findById(itemId));
            addComment(comment, item.getId());
            item.setRating(rating);
            Double rate = item.getRating();
            rate += rating;
            item.setRating(rate);
             //TODO: Придумать формулу
        }
        else {
            throw new BadRequestException("Rating can't be null!");
        }

    }
}
