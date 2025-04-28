package com.example.LunchRoulette.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewResponseDto {

    private Long id;
    private int rating;
    private String comment;
    private String userName;
    private String userSurname;
    private Long restaurantId;
}
