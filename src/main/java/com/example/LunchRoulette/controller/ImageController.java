package com.example.LunchRoulette.controller;

import com.example.LunchRoulette.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload-restaurant-image/{id}")
    public ResponseEntity<Void> uploadRestaurantImage(@PathVariable Long id,
                                                        @RequestParam("file") MultipartFile file) {
        imageService.uploadRestaurantImage(id, file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/upload-dish-image/{id}")
    public ResponseEntity<Void> uploadDishImage(@PathVariable Long id,
                                                  @RequestParam("file") MultipartFile file) {
        imageService.uploadDishImage(id, file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
