package com.example.LunchRoulette.service.impl;

import com.example.LunchRoulette.dto.request.PageRequestDto;
import com.example.LunchRoulette.dto.request.ReviewRequestDto;
import com.example.LunchRoulette.dto.response.ReviewResponseDto;
import com.example.LunchRoulette.entities.Review;
import com.example.LunchRoulette.entities.Restaurant;
import com.example.LunchRoulette.entities.User;
import com.example.LunchRoulette.exception.DbNotFoundException;
import com.example.LunchRoulette.mapper.ReviewMapper;
import com.example.LunchRoulette.repository.ReviewRepository;
import com.example.LunchRoulette.repository.RestaurantRepository;
import com.example.LunchRoulette.repository.UserRepository;
import com.example.LunchRoulette.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewResponseDto createReview(Long restaurantId, ReviewRequestDto dto) {
        // 1) Проверяем ресторан
        Restaurant restaurant = restaurantRepository.findByIdAndIsDeletedFalse(restaurantId)
                .orElseThrow(() -> new DbNotFoundException(
                        HttpStatus.NOT_FOUND.getReasonPhrase(), "Restaurant not found"));

        // 2) Получаем текущего пользователя из SecurityContext
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username;
        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        log.info("User name:{}", username);
        User user = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new DbNotFoundException(
                        HttpStatus.NOT_FOUND.getReasonPhrase(), "User not found"));

        // 3) Мапим ReviewRequestDto → Review (без связей)
        Review review = reviewMapper.toEntity(dto);

        // 4) Привязываем ресторан и пользователя
        review.setRestaurant(restaurant);
        review.setUser(user);

        // 5) Сохраняем и мапим в ReviewResponseDto
        Review saved = reviewRepository.save(review);
        return reviewMapper.toResponse(saved);
    }

    @Override
    public Page<ReviewResponseDto> getAllReviews(Long restaurantId, PageRequestDto pageRequestDto) {
        Pageable pageable = pageRequestDto.getPageable(pageRequestDto);

        Page<Review> page = reviewRepository.findAllByRestaurantIdAndIsDeletedFalse(restaurantId, pageable);

        return page.map(reviewMapper::toResponse);
    }

    @Override
    public void deleteReview(Long restaurantId, Long reviewId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username;
        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User currentUser = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new DbNotFoundException(
                        HttpStatus.NOT_FOUND.getReasonPhrase(), "User not found"));

        Review review = reviewRepository.findByIdAndRestaurantIdAndIsDeletedFalse(reviewId, restaurantId)
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(), "Review not found"));

        if (!review.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You are not allowed to delete this review.");
        }

        review.delete();
        reviewRepository.save(review);
    }
}
