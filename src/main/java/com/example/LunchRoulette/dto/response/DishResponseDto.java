package com.example.LunchRoulette.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DishResponseDto {

    private Long id;
    private String name;
    private String description;
    private int price;
    private String imageUrl;
    private CuisineDto cuisine;

    @Data
    @Builder
    public static class CuisineDto {
        private Long id;
        private String name;
    }
}
