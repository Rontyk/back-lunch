package com.example.LunchRoulette.mapper;

import com.example.LunchRoulette.dto.request.DishRequestDto;
import com.example.LunchRoulette.dto.response.DishResponseDto;
import com.example.LunchRoulette.entities.Dish;
import com.example.LunchRoulette.entities.Cuisine;
import org.springframework.stereotype.Component;

@Component
public class DishMapper {

    public DishResponseDto toResponse(Dish dish) {
        return DishResponseDto.builder()
                .id(dish.getId())
                .name(dish.getName())
                .description(dish.getDes())
                .price(dish.getPrice())
                .imageUrl(dish.getImageUrl())
                .cuisine(DishResponseDto.CuisineDto.builder()
                        .id(dish.getCuisine().getId())
                        .name(dish.getCuisine().getName())
                        .build())
                .build();
    }

    public Dish toEntity(DishRequestDto dto) {
        Dish dish = new Dish();
        dish.setName(dto.getName());
        dish.setPrice(dto.getPrice());
        dish.setDes(dto.getDes());

        return dish;
    }
}
