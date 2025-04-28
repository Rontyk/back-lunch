package com.example.LunchRoulette.mapper;

import com.example.LunchRoulette.dto.response.RestaurantResponseDto;
import com.example.LunchRoulette.entities.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {
    public RestaurantResponseDto toResponse(Restaurant restaurant) {
        return RestaurantResponseDto.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .rating(restaurant.getRating())
                .averagePrice(restaurant.getAveragePrice())
                .maxWaitTime(restaurant.getMaxWaitTime())
                .imageUrl(restaurant.getImageUrl())
                .cuisines(
                        restaurant.getCuisines().stream()
                                .map(cuisine -> {
                                    RestaurantResponseDto.Cuisines dto = new RestaurantResponseDto.Cuisines();
                                    dto.setId(cuisine.getId());
                                    dto.setName(cuisine.getName());
                                    return dto;
                                })
                                .toList()
                )
                .build();
    }
}
