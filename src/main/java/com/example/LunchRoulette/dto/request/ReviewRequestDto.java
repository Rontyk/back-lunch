package com.example.LunchRoulette.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReviewRequestDto {

    private int rating;

    @NotBlank
    private String comment;
}
