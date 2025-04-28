package com.example.LunchRoulette.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RestaurantRequestDto {
    private String name;
    private String address;
    private int averagePrice;
    private int maxWaitTime;
    private String imageUrl;
    private Set<Long> cuisineIds;
}
