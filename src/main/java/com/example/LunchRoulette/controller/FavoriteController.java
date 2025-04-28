package com.example.LunchRoulette.controller;

import com.example.LunchRoulette.dto.response.RestaurantResponseDto;
import com.example.LunchRoulette.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<Page<RestaurantResponseDto>> getFavorites(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(favoriteService.getFavorites(page, size));
    }

    @PostMapping("/{restaurantId}")
    public ResponseEntity<Void> addFavorite(@PathVariable Long restaurantId) {
        favoriteService.addFavorite(restaurantId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long restaurantId) {
        favoriteService.removeFavorite(restaurantId);
        return ResponseEntity.noContent().build();
    }
}
