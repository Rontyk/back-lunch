package com.example.LunchRoulette.service.impl;

import com.example.LunchRoulette.dto.request.DishRequestDto;
import com.example.LunchRoulette.dto.request.PageRequestDto;
import com.example.LunchRoulette.dto.response.DishResponseDto;
import com.example.LunchRoulette.entities.Dish;
import com.example.LunchRoulette.entities.Restaurant;
import com.example.LunchRoulette.exception.DbNotFoundException;
import com.example.LunchRoulette.mapper.DishMapper;
import com.example.LunchRoulette.repository.DishRepository;
import com.example.LunchRoulette.repository.RestaurantRepository;
import com.example.LunchRoulette.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
    private final DishMapper dishMapper;

    @Override
    public Page<DishResponseDto> getDishes(Long restaurantId, Long cuisineId, PageRequestDto pageRequestDto) {
        Restaurant restaurant = restaurantRepository.findByIdAndIsDeletedFalse(restaurantId)
                .orElseThrow(() -> new DbNotFoundException(
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "Restaurant not found"));

        Pageable pageable = pageRequestDto.getPageable(pageRequestDto);
        Page<Dish> page;
        if (cuisineId != null) {
            page = dishRepository.findAllByRestaurantIdAndCuisineIdAndIsDeletedFalse(
                    restaurantId, cuisineId, pageable);
        } else {
            page = dishRepository.findAllByRestaurantIdAndIsDeletedFalse(restaurantId, pageable);
        }
        return page.map(dishMapper::toResponse);
    }

    @Override
    public DishResponseDto getDish(Long restaurantId, Long dishId) {
        Dish dish = dishRepository.findById(dishId)
                .filter(d -> !d.isDeleted() && d.getRestaurant().getId().equals(restaurantId))
                .orElseThrow(() -> new DbNotFoundException(
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "Dish not found in this restaurant"));
        return dishMapper.toResponse(dish);
    }

    @Override
    public DishResponseDto createDish(Long restaurantId, DishRequestDto requestDto) {
        Restaurant restaurant = restaurantRepository.findByIdAndIsDeletedFalse(restaurantId)
                .orElseThrow(() -> new DbNotFoundException(
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "Restaurant not found"));

        Dish dish = dishMapper.toEntity(requestDto);
        dish.setRestaurant(restaurant);
        Dish saved = dishRepository.save(dish);
        return dishMapper.toResponse(saved);
    }

    @Override
    public DishResponseDto updateDish(Long restaurantId, Long dishId, DishRequestDto requestDto) {
        Dish dish = dishRepository.findById(dishId)
                .filter(d -> !d.isDeleted() && d.getRestaurant().getId().equals(restaurantId))
                .orElseThrow(() -> new DbNotFoundException(
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "Dish not found in this restaurant"));

        dish.setName(requestDto.getName());
        dish.setPrice(requestDto.getPrice());
        dish.setDes(requestDto.getDes());
        Dish updated = dishRepository.save(dish);
        return dishMapper.toResponse(updated);
    }

    @Override
    public void deleteDish(Long restaurantId, Long dishId) {
        Dish dish = dishRepository.findById(dishId)
                .filter(d -> !d.isDeleted() && d.getRestaurant().getId().equals(restaurantId))
                .orElseThrow(() -> new DbNotFoundException(
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "Dish not found in this restaurant"));
        dish.delete();
        dishRepository.save(dish);
    }
}
