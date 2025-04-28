package com.example.LunchRoulette.service.impl;

import com.example.LunchRoulette.dto.response.RestaurantResponseDto;
import com.example.LunchRoulette.entities.Restaurant;
import com.example.LunchRoulette.entities.User;
import com.example.LunchRoulette.exception.DbNotFoundException;
import com.example.LunchRoulette.mapper.RestaurantMapper;
import com.example.LunchRoulette.repository.RestaurantRepository;
import com.example.LunchRoulette.repository.UserRepository;
import com.example.LunchRoulette.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    @Override
    public Page<RestaurantResponseDto> getFavorites(int page, int size) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username;
        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new DbNotFoundException(
                        HttpStatus.NOT_FOUND.getReasonPhrase(), "User not found"));
        List<RestaurantResponseDto> favoriteDtos = user.getFavorites().stream()
                .map(restaurantMapper::toResponse)
                .toList();

        Pageable pageable = PageRequest.of(page, size);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), favoriteDtos.size());

        List<RestaurantResponseDto> pagedList = favoriteDtos.subList(start, end);

        return new PageImpl<>(pagedList, pageable, favoriteDtos.size());
    }

    @Override
    public void addFavorite(Long restaurantId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username;
        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new DbNotFoundException(
                        HttpStatus.NOT_FOUND.getReasonPhrase(), "User not found"));
        Restaurant restaurant = restaurantRepository.findByIdAndIsDeletedFalse(restaurantId)
                .orElseThrow(() -> new DbNotFoundException(
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "Restaurant not found"));

        if (user.getFavorites().contains(restaurant)) {
            throw new IllegalStateException("Restaurant already in favorites");
        }

        user.getFavorites().add(restaurant);
        userRepository.save(user);
    }

    @Override
    public void removeFavorite(Long restaurantId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username;
        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new DbNotFoundException(
                        HttpStatus.NOT_FOUND.getReasonPhrase(), "User not found"));
        Restaurant restaurant = restaurantRepository.findByIdAndIsDeletedFalse(restaurantId)
                .orElseThrow(() -> new DbNotFoundException(
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "Restaurant not found"));

        if (!user.getFavorites().contains(restaurant)) {
            throw new DbNotFoundException(
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    "Favorite not found");

        }

        user.getFavorites().remove(restaurant);
        userRepository.save(user);
    }
}
