package ru.mp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mp.dto.item.ItemFilterDto;
import ru.mp.dto.item.ItemInputDto;
import ru.mp.dto.item.ItemOutDto;
import ru.mp.dto.item.ReviewDto;
import ru.mp.entity.ItemEntity;
import ru.mp.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/api/service/item")
@RequiredArgsConstructor
@Tag(name = "Item",description = "Действия с предметом")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_MODERATOR' or 'ROLE_OWNER' and 'ROLE_USER')")
    @Operation(description = "Получение списка предметов")
    public List<ItemOutDto> getItems() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_OWNER')")
    @Operation(description = "Получение предметов по уникальному идентификатору")
    public ItemOutDto getItemById(@PathVariable Integer id) {
        return itemService.findById(id);
    }

    @GetMapping("/byName/{name}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_MODERATOR' and 'ROLE_OWNER' and 'ROLE_USER')")
    @Operation(description = "Получение предметов по его название")
    public ItemOutDto getItemByName(@PathVariable String name) {
        return itemService.findByName(name);
    }

    @GetMapping("/sort")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_MODERATOR' and 'ROLE_OWNER' and 'ROLE_USER')")
    @Operation(description = "Получение предметов с фильтрацией")
    public List<ItemOutDto> getItemsBySort(ItemFilterDto itemFilterDto) {
        return itemService.findWithSorting(itemFilterDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создание предмтеа")
    @ApiResponses({
            @ApiResponse(description = "Создание предмета",responseCode = "201"),
            @ApiResponse(description = "Неудачная попытка создания предмета",responseCode = "400"),
            @ApiResponse(description = "Непредвиденная ошибка сервера . . .",responseCode = "500")
    })
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public void createItem(@RequestBody ItemInputDto itemInputDto) {
        itemService.saveItem(itemInputDto);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_MODERATOR' and 'ROLE_OWNER')")
    @Operation(description = "Обновление предмета")
    @ApiResponses({
            @ApiResponse(description = "Обновление предмета прошло успешно",responseCode = "200"),
            @ApiResponse(description = "Неудачная попытка обновления предмета",responseCode = "400"),
            @ApiResponse(description = "Не удалось найти предмет",responseCode = "404"),
            @ApiResponse(description = "Непредвиденная ошибка сервера . . .",responseCode = "500")
    })
    public void editItem(@PathVariable Integer id, @RequestBody ItemInputDto itemInputDto) {
        itemService.updateItem(id, itemInputDto);
    }

    @PatchMapping("/edit/{id}")
    @Operation(description = "Редактирование предмета")
    @ApiResponses({
            @ApiResponse(description = "Обновления предмета",responseCode = "200"),
            @ApiResponse(description = "Неудачная попытка обновления предмета",responseCode = "400"),
            @ApiResponse(description = "Не удалось найти предмет",responseCode = "404"),
            @ApiResponse(description = "Непредвиденная ошибка сервера . . .",responseCode = "500")
    })
    public void updateItem(@PathVariable Integer id, @RequestBody String comment) {
        itemService.addComment(comment, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_OWNER' and 'ROLE_MODERATOR')")
    @Operation(description = "Удаления предмета по уникальному идентификатору")
    public void deleteItem(@PathVariable Integer id) {
        itemService.deleteItemById(id);
    }

    @PatchMapping("/review/{id}")
    @PreAuthorize("hasRole('ROLE_OWNER' and 'ROLE_USER')")
    @Operation(description = "Добавление отзыва к товару")
    @ResponseStatus(HttpStatus.OK)
    public void addReviews(@PathVariable Integer id,
                           @RequestBody ReviewDto reviewDto
                           ){
        itemService.makeReview(id,reviewDto);
    }





}
