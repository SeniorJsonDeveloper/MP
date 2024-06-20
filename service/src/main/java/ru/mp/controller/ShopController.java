package ru.mp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mp.dto.shop.ShopFilterDto;
import ru.mp.dto.shop.ShopInputDto;
import ru.mp.dto.shop.ShopOutDto;
import ru.mp.service.ShopService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service/shop")
@Tag(name = "Shop",description = "Действия с магазином")
public class ShopController {

    private final ShopService shopService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_MODERATOR' and 'ROLE_OWNER' and 'ROLE_USER')")
    @Operation(description = "Получение списка магазинов")
    public List<ShopOutDto> getAllShops(){
        return shopService.getAllShops();
    }

    @GetMapping("/sort")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_MODERATOR' and 'ROLE_OWNER' and 'ROLE_USER')")
    @Operation(description = "Получение списка магазинов с фильтрацией")
    public List<ShopOutDto> getShopsSort(ShopFilterDto shopFilterDto){
        return shopService.getShopWithSort(shopFilterDto);
    }

    @GetMapping("/{shopname}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_MODERATOR' and 'ROLE_OWNER' and 'ROLE_USER')")
    @Operation(description = "Получение  магазина по его названию")
    public ShopOutDto getByShopname(@PathVariable String shopname){
        return shopService.findByName(shopname);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_MODERATOR' and 'ROLE_OWNER')")
    @Operation(description = "Получение  магазина по уникальному идентификатору")
    public ShopOutDto getById(@PathVariable Integer id){
        return shopService.getShopById(id);
    }

    @PostMapping("/registry")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_OWNER')")
    @Operation(description = "Регистрация магазина")
    @ApiResponses({
            @ApiResponse(description = "Регистрация магазина",responseCode = "201"),
            @ApiResponse(description = "Неудачная попытка регистрации магазина",responseCode = "400"),
            @ApiResponse(description = "Непредвиденная ошибка сервера . . .",responseCode = "500")
    })
    public void registryShop(@RequestBody ShopInputDto shopInputDto){
        shopService.registerShop(shopInputDto);
    }

    @PatchMapping("/edit/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_OWNER' or 'ROLE_SHOPOWNER')")
    @Operation(description = "Редактирование магазина")
    @ApiResponses({
            @ApiResponse(description = "Редактирование магазина",responseCode = "200"),
            @ApiResponse(description = "Неудачная попытка редактирования магазина",responseCode = "400"),
            @ApiResponse(description = "Не удалось найти магазин",responseCode = "404"),
            @ApiResponse(description = "Непредвиденная ошибка сервера . . .",responseCode = "500")
    })
    public void editShop(@PathVariable Integer id, @RequestBody ShopInputDto shopInputDto){
        shopService.updateShop(id,shopInputDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_OWNER' or 'ROLE_SHOPOWNER')")
    @Operation(description = "Удаление магазина по уникальному идентификатору")
    public void deleteShop(@PathVariable Integer id){
        shopService.deleteShop(id);
    }
}
