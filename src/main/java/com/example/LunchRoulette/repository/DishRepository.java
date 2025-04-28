package com.example.LunchRoulette.repository;

import com.example.LunchRoulette.entities.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {

    // Все блюда для ресторана, не удалённые, с пагинацией
    Page<Dish> findAllByRestaurantIdAndIsDeletedFalse(Long restaurantId, Pageable pageable);

    // Отфильтрованные по кухне
    Page<Dish> findAllByRestaurantIdAndCuisineIdAndIsDeletedFalse(
            Long restaurantId,
            Long cuisineId,
            Pageable pageable
    );
}


