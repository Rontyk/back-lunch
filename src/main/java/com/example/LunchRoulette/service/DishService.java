package com.example.LunchRoulette.service;

import com.example.LunchRoulette.dto.request.DishRequestDto;
import com.example.LunchRoulette.dto.request.PageRequestDto;
import com.example.LunchRoulette.dto.response.DishResponseDto;
import org.springframework.data.domain.Page;

public interface DishService {

    Page<DishResponseDto> getDishes(Long restaurantId, Long cuisineId, PageRequestDto pageRequestDto);

    DishResponseDto getDish(Long restaurantId, Long dishId);

    DishResponseDto createDish(Long restaurantId, DishRequestDto requestDto);

    DishResponseDto updateDish(Long restaurantId, Long dishId, DishRequestDto requestDto);

    void deleteDish(Long restaurantId, Long dishId);
}



