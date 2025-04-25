package com.example.LunchRoulette.repository;

import com.example.LunchRoulette.entities.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Long> {
}
