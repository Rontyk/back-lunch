package com.example.LunchRoulette.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends AbstractEntity<Long>{
    private int rating; // 1–5
    private String comment;

    @ManyToOne
    private User user;

    @ManyToOne
    private Restaurant restaurant;
}
