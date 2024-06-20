package ru.mp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mp.dto.deal.DealCreateDto;
import ru.mp.dto.deal.DealOutDto;
import ru.mp.service.DealService;

import java.util.List;

@RestController
@RequestMapping("/api/service/deal")
@RequiredArgsConstructor
@Tag(name = "Deal",description = "Действия со сделкой")
public class DealController {

    private final DealService dealService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_MODERATOR' or 'ROLE_OWNER')")
    @Operation(description = "Получение списка cделок")
    public List<DealOutDto> getAllDeals(){
        return dealService.findAllDeals();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_MODERATOR' or 'ROLE_OWNER')")
    @Operation(description = "Получение сделки по уникальному идентификатору")
    public DealOutDto getDealById(@PathVariable Integer id){
        return dealService.findById(id);
    }

    @PostMapping("/make")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_OWNER')")
    @Operation(description = "Создание сделки")
    @ApiResponses({
            @ApiResponse(description = "Создание сделки",responseCode = "200"),
            @ApiResponse(description = "Неудачная попытка создания сделки",responseCode = "400"),
            @ApiResponse(description = "Непредвиденная ошибка сервера . . .",responseCode = "500")
    })
    public DealOutDto makeDeal(@RequestBody DealCreateDto dealCreateDto){
        return dealService.createDeal(dealCreateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_MODERATOR' or 'ROLE_OWNER')")
    @Operation(description = "Удаление сделки по уникальному идентификатору")
    public void deleteDeal(@PathVariable Integer id){
        dealService.deleteDeal(id);
    }
}
