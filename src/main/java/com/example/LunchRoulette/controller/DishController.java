package com.example.LunchRoulette.controller;

import com.example.LunchRoulette.dto.request.DishRequestDto;
import com.example.LunchRoulette.dto.request.PageRequestDto;
import com.example.LunchRoulette.dto.response.DishResponseDto;
import com.example.LunchRoulette.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurants/{restaurantId}/dishes")
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    @GetMapping
    public ResponseEntity<Page<DishResponseDto>> getDishesByRestaurant(
            @PathVariable Long restaurantId,
            @RequestParam(required = false) Long cuisineId,
            PageRequestDto pageRequestDto
    ) {
        Page<DishResponseDto> page = dishService.getDishes(restaurantId, cuisineId, pageRequestDto);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{dishId}")
    public ResponseEntity<DishResponseDto> getDish(
            @PathVariable Long restaurantId,
            @PathVariable Long dishId
    ) {
        DishResponseDto dto = dishService.getDish(restaurantId, dishId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<DishResponseDto> createDish(
            @PathVariable Long restaurantId,
            @RequestBody DishRequestDto requestDto
    ) {
        DishResponseDto created = dishService.createDish(restaurantId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{dishId}")
    public ResponseEntity<DishResponseDto> updateDish(
            @PathVariable Long restaurantId,
            @PathVariable Long dishId,
            @RequestBody @Valid DishRequestDto requestDto
    ) {
        DishResponseDto updated = dishService.updateDish(restaurantId, dishId, requestDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{dishId}")
    public ResponseEntity<Void> deleteDish(
            @PathVariable Long restaurantId,
            @PathVariable Long dishId
    ) {
        dishService.deleteDish(restaurantId, dishId);
        return ResponseEntity.noContent().build();
    }
}