package com.example.LunchRoulette.controller;

import com.example.LunchRoulette.dto.request.PageRequestDto;
import com.example.LunchRoulette.dto.request.RestaurantRequestDto;
import com.example.LunchRoulette.dto.response.RestaurantResponseDto;
import com.example.LunchRoulette.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    @GetMapping
    public ResponseEntity<Page<RestaurantResponseDto>> getAllRestaurants(PageRequestDto dto){
        return ResponseEntity.ok(restaurantService.getAllRestaurants(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDto> getRestaurant(@PathVariable Long id){
        return ResponseEntity.ok(restaurantService.getRestaurant(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRestaurant(@PathVariable Long id,
                                                                  @RequestBody RestaurantRequestDto requestDto) {
        restaurantService.updateRestaurant(id, requestDto);
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }


}
