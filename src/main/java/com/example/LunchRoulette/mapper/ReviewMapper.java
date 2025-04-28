package com.example.LunchRoulette.mapper;

import com.example.LunchRoulette.dto.request.ReviewRequestDto;
import com.example.LunchRoulette.dto.response.ReviewResponseDto;
import com.example.LunchRoulette.entities.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public Review toEntity(ReviewRequestDto dto) {
        Review r = new Review();
        r.setRating(dto.getRating());
        r.setComment(dto.getComment());
        return r;
    }

    public ReviewResponseDto toResponse(Review review) {
        return ReviewResponseDto.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .userName(review.getUser().getFirstName())
                .userSurname(review.getUser().getLastName())
                .restaurantId(review.getRestaurant().getId())
                .build();
    }
}
