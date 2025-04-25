package com.example.LunchRoulette.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageService {
    void uploadRestaurantImage(Long id, MultipartFile file);

    void uploadDishImage(Long id, MultipartFile file);
}
