package ru.mp.service;

import ru.mp.dto.item.ItemFilterDto;
import ru.mp.dto.item.ItemInputDto;
import ru.mp.dto.item.ItemOutDto;
import ru.mp.dto.item.ReviewDto;
import ru.mp.entity.ItemEntity;

import java.util.List;

public interface ItemService {

    List<ItemOutDto> findAll();

    ItemOutDto findById(Integer id);

    List<ItemOutDto> findWithSorting(ItemFilterDto itemFilterDto);

    ItemOutDto findByName(String name);

    void deleteItemById(Integer id);

    void addComment(String comment,Integer id);

    void saveItem(ItemInputDto itemInputDto);

    void updateItem(Integer id,ItemInputDto itemInputDto);

    void deleteAllItems(List<Integer> ids);

    void makeReview(Integer itemId, ReviewDto reviewDto);
}
