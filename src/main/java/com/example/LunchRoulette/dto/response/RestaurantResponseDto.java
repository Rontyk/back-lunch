package com.example.LunchRoulette.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class RestaurantResponseDto {
    private Long id;
    private String name;
    private String address;
    private double rating;
    private int averagePrice;
    private int maxWaitTime;
    private String imageUrl;
    private List<Cuisines> cuisines;

    @Data
    public static class Cuisines{
        private Long id;
        private String name;
    }
}
