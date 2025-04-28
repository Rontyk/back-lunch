package com.example.LunchRoulette.repository;

import com.example.LunchRoulette.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByRestaurantIdAndIsDeletedFalse(Long restaurantId, Pageable pageable);

    Optional<Review> findByIdAndRestaurantIdAndIsDeletedFalse(Long reviewId, Long restaurantId);
}
