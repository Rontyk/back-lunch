package com.example.LunchRoulette.service;

import com.example.LunchRoulette.dto.response.RestaurantResponseDto;
import org.springframework.data.domain.Page;

public interface FavoriteService {

    Page<RestaurantResponseDto> getFavorites(int page, int size);

    void addFavorite(Long restaurantId);

    void removeFavorite(Long restaurantId);
}
