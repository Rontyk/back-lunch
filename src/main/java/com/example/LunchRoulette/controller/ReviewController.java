package com.example.LunchRoulette.controller;

import com.example.LunchRoulette.dto.request.PageRequestDto;
import com.example.LunchRoulette.dto.request.ReviewRequestDto;
import com.example.LunchRoulette.dto.response.ReviewResponseDto;
import com.example.LunchRoulette.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurants/{restaurantId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(
            @PathVariable Long restaurantId,
            @RequestBody @Valid ReviewRequestDto dto
    ) {
        ReviewResponseDto created = reviewService.createReview(restaurantId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<Page<ReviewResponseDto>> getAllReviews(
            @PathVariable Long restaurantId,
            PageRequestDto pageRequestDto
    ) {
        Page<ReviewResponseDto> reviews = reviewService.getAllReviews(restaurantId, pageRequestDto);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long restaurantId,
            @PathVariable Long reviewId
    ) {
        reviewService.deleteReview(restaurantId, reviewId);
        return ResponseEntity.noContent().build();
    }
}
