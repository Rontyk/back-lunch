package com.example.LunchRoulette.service;

import com.example.LunchRoulette.dto.request.PageRequestDto;
import com.example.LunchRoulette.dto.request.RestaurantRequestDto;
import com.example.LunchRoulette.dto.response.RestaurantResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface RestaurantService {
    Page<RestaurantResponseDto> getAllRestaurants(PageRequestDto dto);

    RestaurantResponseDto getRestaurant(Long id);

    void updateRestaurant(Long id, RestaurantRequestDto dto);

    void deleteRestaurant(Long id);
}
