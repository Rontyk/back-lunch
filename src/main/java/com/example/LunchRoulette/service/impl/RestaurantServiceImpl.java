package com.example.LunchRoulette.service.impl;

import com.example.LunchRoulette.dto.request.PageRequestDto;
import com.example.LunchRoulette.dto.request.RestaurantRequestDto;
import com.example.LunchRoulette.dto.response.RestaurantResponseDto;
import com.example.LunchRoulette.entities.Cuisine;
import com.example.LunchRoulette.entities.Restaurant;
import com.example.LunchRoulette.exception.DbNotFoundException;
import com.example.LunchRoulette.mapper.RestaurantMapper;
import com.example.LunchRoulette.repository.CuisineRepository;
import com.example.LunchRoulette.repository.RestaurantRepository;
import com.example.LunchRoulette.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final CuisineRepository cuisineRepository;

    @Override
    public Page<RestaurantResponseDto> getAllRestaurants(PageRequestDto dto) {
        Pageable pageable = dto.getPageable(dto);
        return restaurantRepository
                .findAllByIsDeletedFalse(pageable)
                .map(restaurantMapper::toResponse);
    }

    @Override
    public RestaurantResponseDto getRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .filter(f -> !f.isDeleted())
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "Restaurant with this id do not found"));
        return restaurantMapper.toResponse(restaurant);

    }

    @Override
    public void updateRestaurant(Long id, RestaurantRequestDto dto) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .filter(f -> !f.isDeleted())
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "Restaurant not found with this id "));

        restaurant.setName(dto.getName());
        restaurant.setAddress(dto.getAddress());
        restaurant.setAveragePrice(dto.getAveragePrice());
        restaurant.setMaxWaitTime(dto.getMaxWaitTime());
        if (dto.getCuisineIds() != null) {
            Set<Cuisine> cuisines = dto.getCuisineIds().stream()
                    .map(cuisineRepository::findById)
                    .map(opt -> opt.orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Cuisine not found")))
                    .collect(Collectors.toSet());
            restaurant.setCuisines(cuisines);
        }

        restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .filter(f -> !f.isDeleted())
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Restaurant fo not found"));

        restaurant.delete();
        restaurantRepository.save(restaurant);
    }
}
