package com.example.LunchRoulette.repository;

import com.example.LunchRoulette.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
