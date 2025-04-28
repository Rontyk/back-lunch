package com.example.LunchRoulette.repository;

import com.example.LunchRoulette.entities.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Page<Restaurant> findAllByIsDeletedFalse(Pageable pageable);
    Optional<Restaurant> findByIdAndIsDeletedFalse(Long id);
}
