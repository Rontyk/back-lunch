package com.example.LunchRoulette.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.LunchRoulette.entities.Dish;
import com.example.LunchRoulette.entities.Restaurant;
import com.example.LunchRoulette.repository.DishRepository;
import com.example.LunchRoulette.repository.RestaurantRepository;
import com.example.LunchRoulette.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DishRepository dishRepository;

    public void uploadRestaurantImage(Long id, MultipartFile file) {
        String imageUrl = uploadToCloudinary(file);
        log.info("file: {}", imageUrl);
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow();
        log.info("1");
        restaurant.setImageUrl(imageUrl);
        log.info("2");
        restaurantRepository.save(restaurant);
    }

    public void uploadDishImage(Long id, MultipartFile file) {
        String imageUrl = uploadToCloudinary(file);
        Dish dish = dishRepository.findById(id).orElseThrow();
        dish.setImageUrl(imageUrl);
        dishRepository.save(dish);
    }

    private String uploadToCloudinary(MultipartFile file) {
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException("Image upload failed", e);
        }
    }
}
