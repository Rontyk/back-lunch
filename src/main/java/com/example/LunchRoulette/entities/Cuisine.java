package com.example.LunchRoulette.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cuisine extends AbstractEntity<Long>{

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "cuisines")
    private Set<Restaurant> restaurants = new HashSet<>();
}

