package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;

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
    private final BookingService2 bookingService;

    public BookingController(BookingService2 bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingDto add(@RequestHeader("X-Sharer-User-Id") Long bookerId,
                       @Valid @RequestBody BookingDto bookingDto) {
        log.info("добавить заказ для пользователя userId={} и предмета itemId={}", bookerId, bookingDto.getItemId());
        log.info("booking для добавления={}", bookingDto);
        return bookingService.add(bookerId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto update(@RequestHeader("X-Sharer-User-Id") Long ownerId, @PathVariable Long bookingId,
                           @RequestParam Boolean approved) {
        log.info("изменить для пользователя userId={} данные bookingId={}", ownerId, bookingId);
        log.info("данные для изменения={}", approved);
        return bookingService.update(ownerId, bookingId, approved);
    }

    @DeleteMapping("/{bookingId}")
    public void delete(@RequestHeader("X-Sharer-User-Id") Long requesterId, @PathVariable Long bookingId) {
        log.info("удалить заказ с id={}", requesterId);
        bookingService.delete(requesterId, bookingId);
    }

    @GetMapping
    public List<BookingDto> getUsersBookings(@RequestHeader("X-Sharer-User-Id") Long bookerId,
                                             @Valid @RequestParam(defaultValue = "ALL") String state) {
        log.info("получить заказы по заказчику={}", bookerId);
        return bookingService.getBookersBookings(bookerId, state);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getOne(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long bookingId) {
        log.info("получить заказ для bookingId={}",bookingId);
        return bookingService.getOne(userId, bookingId);
    }

    @GetMapping("/owner")
    public List<BookingDto> getOwnersBookings(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                             @Valid @RequestParam(defaultValue = "ALL") String state) {
        log.info("получить заказы по владельцу={}", ownerId);
        return bookingService.getOwnersBookings(ownerId, state);
    }

//    @GetMapping
//    public  List<BookingDto> getByItemIdAndStatus(@RequestParam Long itemId, @RequestParam(required = false) BookingStatus status) {
//        log.info("получить заказы для itemId={}",itemId);
//        return bookingService.getByItemIdAndStatus(itemId, status);
//    }

//    @PostMapping("/{bookingId}/review")
//    public ReviewDto addReview(@RequestHeader("X-Sharer-User-Id") Long requesterId,
//                            @PathVariable Long bookingId, @Valid @RequestBody ReviewDto reviewDto) {
//        log.info("добавить отзыв для bookingId={}", bookingId);
//        log.info("отзыв для добавления={}", reviewDto);
//        return bookingService.addReview(requesterId, bookingId, reviewDto);
//    }
//
//    @PatchMapping("/{bookingId}/review")
//    public ReviewDto updateReview(@RequestHeader("X-Sharer-User-Id") Long requesterId,
//                               @PathVariable Long bookingId, @RequestBody ReviewDto reviewDto) {
//        log.info("изменить для пользователя bookingId={} данные requesterId={}", bookingId, requesterId);
//        log.info("данные для изменения={}", reviewDto);
//        return bookingService.updateReview(requesterId, bookingId, reviewDto);
//    }
//
//    @DeleteMapping("/review/{reviewId}")
//    public void deleteReview(@RequestHeader("X-Sharer-User-Id") Long requesterId, @PathVariable Long reviewId) {
//        log.info("удалить отзыв с id={}", reviewId);
//        bookingService.deleteReview(requesterId, reviewId);
//    }
//
//    @GetMapping("/review/{itemId}")
//    public List<ReviewDto> getReviewsForItem(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long itemId) {
//        log.info("получить заказы по предмету={}", itemId);
//        return bookingService.getReviewsForItem(userId, itemId);
//    }

}
