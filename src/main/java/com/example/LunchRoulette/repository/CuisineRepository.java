package com.example.LunchRoulette.repository;

import com.example.LunchRoulette.entities.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuisineRepository extends JpaRepository<Cuisine, Long> {

}
