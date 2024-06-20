package ru.mp.service;

import ru.mp.dto.deal.DealCreateDto;
import ru.mp.dto.deal.DealOutDto;

import java.util.List;

public interface DealService {

    DealOutDto findById(Integer id);

    List<DealOutDto> findAllDeals();

    DealOutDto createDeal(DealCreateDto dealCreateDto);

    void editDeal(Integer id, DealCreateDto dealCreateDto);

    void deleteDeal(Integer id);

}
