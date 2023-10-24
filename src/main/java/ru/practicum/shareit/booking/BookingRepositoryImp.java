package ru.practicum.shareit.booking;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.ReviewDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Review;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.EntityNotFoundException;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BookingRepositoryImp implements BookingRepository {
    private final Map<Long, Booking> bookingsMap = new HashMap<>();
    private final Map<Long, Review> reviewsMap = new HashMap<>();
    private Long globalId = 1L;

    @Override
    public Booking add(Long userId, Long itemId, Booking booking) {
        booking.setId(globalId);

//        booking.setItemId(itemId);
//        booking.setBookerId(userId);
        booking.setStatus(BookingStatus.WAITING);

        checkConflictTime(itemId,booking);
        bookingsMap.put(globalId, booking);
        globalId++;
        return booking;
    }

    @Override
    public Booking update(Long userId, Long bookingId, Booking booking) {
        Booking oldBooking = getBooking(bookingId);
//        if (!oldBooking.getItemOwnerId().equals(userId)) {
//            throw new ValidationException("юзер не является владельцем предмета");
//        }
        if (booking.getStatus() != null) {
            oldBooking.setStatus(booking.getStatus());
        }
        return oldBooking;
    }

    public Booking getBooking(Long bookingId) {
        if (bookingsMap.containsKey(bookingId)) {
            return bookingsMap.get(bookingId);
        }
        throw new EntityNotFoundException("не найден booking с id - " + bookingId);
    }

    @Override
    public void delete(Long requesterId, Long bookingId) {
//        if (bookingsMap.get(bookingId).getBookerId().equals(requesterId)) {
//            bookingsMap.remove(bookingId);
//        }
        reviewsMap.values().removeIf(review -> review.getBookingId().equals(bookingId) && review.getClientId().equals(requesterId));
    }

    @Override
    public List<Booking> getUsersBookings(Long ownerId) {
        return null;
//                bookingsMap.values().stream().filter(booking -> booking.getItemOwnerId().equals(ownerId)).collect(Collectors.toList());
    }

    @Override
    public Booking getOne(Long bookingId) {
        return getBooking(bookingId);
    }

    @Override
    public List<Booking> getByItemIdAndStatus(Long itemId, BookingStatus status) {
//        return bookingsMap.values().stream().filter(booking -> {
//            if (status == null) {
//                return booking.getItemId().equals(itemId);
//            } else {
//                return (booking.getItemId().equals(itemId) && booking.getStatus().equals(status));
//            }
//            }).collect(Collectors.toList());
        return null;
    }

    @Override
    public Review addReview(Long requesterId, Long bookingId, Review review) {
        var booking = getBooking(bookingId);

        checkBooking(booking, requesterId);
        review.setId(bookingId);
        review.setBookingId(bookingId);
        review.setClientId(requesterId);
//        review.setReviewedItemId(booking.getItemId());
        reviewsMap.put(bookingId, review);
        return review;
    }

    @Override
    public Review updateReview(Long requesterId, Long bookingId, ReviewDto reviewDto) {
        var booking = getBooking(bookingId);
        checkBooking(booking, requesterId);
        Review reviewToUpdate = getReview(bookingId);
        if (reviewDto.getPositive() != null) {
            reviewToUpdate.setPositive(reviewDto.getPositive());
        }
        if (reviewDto.getOpinion() != null) {
            reviewToUpdate.setOpinion(reviewDto.getOpinion());
        }
        return reviewToUpdate;
    }

    @Override
    public void deleteReview(Long requesterId, Long reviewId) {
        if (reviewsMap.get(reviewId).getClientId().equals(requesterId)) {
            reviewsMap.remove(reviewId);
        }
    }

    @Override
    public List<Review> getReviewsForItem(Long itemId) {
        return reviewsMap.values().stream().filter(review -> review.getReviewedItemId().equals(itemId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByUserId(Long userId) {
//        for (Booking booking : bookingsMap.values()) {
//            if (booking.getBookerId().equals(userId) || booking.getItemOwnerId().equals(userId)) {
//                reviewsMap.remove(booking.getId());
//                bookingsMap.remove(booking.getId());
//            }
//        }
    }

    @Override
    public void deleteByItemId(Long itemId) {
//        for (Booking booking : bookingsMap.values()) {
//            if (booking.getItemId().equals(itemId)) {
//                reviewsMap.remove(booking.getId());
//                bookingsMap.remove(booking.getId());
//            }
//        }
    }

    private Review getReview(Long bookingId) {
        if (reviewsMap.containsKey(bookingId)) {
            return reviewsMap.get(bookingId);
        }
        throw new EntityNotFoundException("не найден отзыв к booking id - " + bookingId);
    }

    private void checkBooking(Booking booking, Long requesterId) {
//        if (!booking.getBookerId().equals(requesterId)) {
//            throw new ValidationException("пользователь не является заказчиком");
//        }
    }

    private void checkConflictTime(Long itemId, Booking booking) {
        var newStart = booking.getStart();
        var newEnd = booking.getEnd();
//        for (Booking oldBooking : bookingsMap.values()) {
//            if (oldBooking.getItemId().equals(itemId) && oldBooking.getStatus().equals(BookingStatus.APPROVED)) {
//                var oldStart = oldBooking.getStart();
//                var oldEnd = oldBooking.getEnd();
//
//                if (oldStart.isBefore(newStart) && oldEnd.isAfter(newStart)) {
//                    throw new ConflictException("время занято");
//                }
//                if (oldStart.isBefore(newEnd) && oldEnd.isAfter(newEnd)) {
//                    throw new ConflictException("время занято");
//                }
//                if (newStart.isBefore(oldStart) && newEnd.isAfter(oldEnd)) {
//                    throw new ConflictException("время занято");
//                }
//            }
//        }
    }
}
