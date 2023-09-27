package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.Review;

public class DtoReviewMapper {

    public static Review dtoToReview(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setBookingId(reviewDto.getBookingId());
        review.setClientId(reviewDto.getClientId());
        review.setReviewedItemId(reviewDto.getReviewedItemId());
        review.setReviewTime(reviewDto.getReviewTime());
        review.setPositive(reviewDto.getPositive());
        review.setOpinion(reviewDto.getOpinion());
        return review;
    }

    public static ReviewDto reviewToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setBookingId(review.getBookingId());
        reviewDto.setClientId(review.getClientId());
        reviewDto.setReviewedItemId(review.getReviewedItemId());
        reviewDto.setReviewTime(review.getReviewTime());
        reviewDto.setPositive(review.getPositive());
        reviewDto.setOpinion(review.getOpinion());
        return reviewDto;
    }

    public static ReviewDto reviewToDtoWithoutUsers(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setBookingId(review.getBookingId());
        reviewDto.setReviewedItemId(review.getReviewedItemId());
        reviewDto.setReviewTime(review.getReviewTime());
        reviewDto.setPositive(review.getPositive());
        reviewDto.setOpinion(review.getOpinion());
        return reviewDto;
    }
}
