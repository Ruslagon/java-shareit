package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.ReviewDto;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@Slf4j
@RestController
@Validated
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/{itemId}")
    public BookingDto add(@RequestHeader("X-Sharer-User-Id") Long bookerId, @PathVariable Long itemId,
                       @Valid @RequestBody BookingDto bookingDto) {
        log.info("добавить заказ для пользователя userId={} и предмета itemId={}", bookerId, itemId);
        log.info("booking для добавления={}", bookingDto);
        return bookingService.add(bookerId, itemId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto update(@RequestHeader("X-Sharer-User-Id") Long ownerId, @PathVariable Long bookingId,
                           @Valid @RequestBody BookingDto bookingDto) {
        log.info("изменить для пользователя userId={} данные bookingId={}", ownerId, bookingId);
        log.info("данные для изменения={}", bookingDto);
        return bookingService.update(ownerId, bookingId, bookingDto);
    }

    @DeleteMapping("/{bookingId}")
    public void delete(@RequestHeader("X-Sharer-User-Id") Long requesterId, @PathVariable Long bookingId) {
        log.info("удалить заказ с id={}", requesterId);
        bookingService.delete(requesterId, bookingId);
    }

    @GetMapping("/myBookings")
    public List<BookingDto> getUsersBookings(@RequestHeader("X-Sharer-User-Id") Long ownerId) {
        log.info("получить заказы по владельцу={}", ownerId);
        return bookingService.getUsersBookings(ownerId);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getOne(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long bookingId) {
        log.info("получить заказ для bookingId={}",bookingId);
        return bookingService.getOne(userId, bookingId);
    }

    @GetMapping
    public  List<BookingDto> getByItemIdAndStatus(@RequestParam Long itemId, @RequestParam(required = false) BookingStatus status) {
        log.info("получить заказы для itemId={}",itemId);
        return bookingService.getByItemIdAndStatus(itemId, status);
    }

    @PostMapping("/{bookingId}/review")
    public ReviewDto addReview(@RequestHeader("X-Sharer-User-Id") Long requesterId,
                            @PathVariable Long bookingId, @Valid @RequestBody ReviewDto reviewDto) {
        log.info("добавить отзыв для bookingId={}", bookingId);
        log.info("отзыв для добавления={}", reviewDto);
        return bookingService.addReview(requesterId, bookingId, reviewDto);
    }

    @PatchMapping("/{bookingId}/review")
    public ReviewDto updateReview(@RequestHeader("X-Sharer-User-Id") Long requesterId,
                               @PathVariable Long bookingId, @RequestBody ReviewDto reviewDto) {
        log.info("изменить для пользователя bookingId={} данные requesterId={}", bookingId, requesterId);
        log.info("данные для изменения={}", reviewDto);
        return bookingService.updateReview(requesterId, bookingId, reviewDto);
    }

    @DeleteMapping("/review/{reviewId}")
    public void deleteReview(@RequestHeader("X-Sharer-User-Id") Long requesterId, @PathVariable Long reviewId) {
        log.info("удалить отзыв с id={}", reviewId);
        bookingService.deleteReview(requesterId, reviewId);
    }

    @GetMapping("/review/{itemId}")
    public List<ReviewDto> getReviewsForItem(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long itemId) {
        log.info("получить заказы по предмету={}", itemId);
        return bookingService.getReviewsForItem(userId, itemId);
    }

}
