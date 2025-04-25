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
public class Favorite extends AbstractEntity<Long>{
    @ManyToOne
    private User user;

    @ManyToOne
    private Restaurant restaurant;
}

