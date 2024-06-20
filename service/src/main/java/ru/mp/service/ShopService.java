package ru.mp.service;

import ru.mp.dto.shop.ShopFilterDto;
import ru.mp.dto.shop.ShopInputDto;
import ru.mp.dto.shop.ShopOutDto;

import java.util.List;

public interface ShopService {

    List<ShopOutDto> getAllShops();

    List<ShopOutDto> getShopWithSort(ShopFilterDto shopFilterDto);

    ShopOutDto findByName(String name);

    ShopOutDto getShopById(Integer id);

    void registerShop(ShopInputDto shopInputDto);

    void updateShop(Integer id,ShopInputDto shopInputDto);

    void deleteShop(Integer id);


}
