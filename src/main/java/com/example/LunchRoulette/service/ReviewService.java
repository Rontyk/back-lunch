package com.example.LunchRoulette.service;

import com.example.LunchRoulette.dto.request.PageRequestDto;
import com.example.LunchRoulette.dto.request.ReviewRequestDto;
import com.example.LunchRoulette.dto.response.ReviewResponseDto;
import org.springframework.data.domain.Page;

public interface ReviewService {
    ReviewResponseDto createReview(Long restaurantId, ReviewRequestDto dto);
    Page<ReviewResponseDto> getAllReviews(Long restaurantId, PageRequestDto pageRequestDto);
    void deleteReview(Long restaurantId, Long reviewId);
}
