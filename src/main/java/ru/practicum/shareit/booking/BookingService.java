package ru.practicum.shareit.booking;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.DtoBookingMapper;
import ru.practicum.shareit.booking.dto.DtoReviewMapper;
import ru.practicum.shareit.booking.dto.ReviewDto;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.UserRepository;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, ItemRepository itemRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public BookingDto add(Long userId, Long itemId, BookingDto booking) {
        userRepository.containsById(userId);

        var item = itemRepository.getOne(itemId);
        if (!item.getAvailable()) {
            throw new ConflictException("предмет заблокирован. itemId - " + itemId);
        }

        //booking.setItemOwnerId(item.getOwnerId());
        checkBookingTime(booking);
        return DtoBookingMapper.bookingToDto(bookingRepository.add(userId, itemId, DtoBookingMapper.dtoToBooking(booking)));
    }

    public BookingDto update(Long userId, Long bookingId, BookingDto booking) {
        userRepository.containsById(userId);
        return DtoBookingMapper.bookingToDto(bookingRepository.update(userId, bookingId, DtoBookingMapper.dtoToBooking(booking)));
    }

    public void delete(Long requesterId, Long bookingId) {
        userRepository.containsById(requesterId);
        bookingRepository.delete(requesterId, bookingId);
    }

    public List<BookingDto> getUsersBookings(Long ownerId) {
        userRepository.containsById(ownerId);
        return bookingRepository.getUsersBookings(ownerId).stream().map(DtoBookingMapper::bookingToDto)
                .collect(Collectors.toList());
    }

    public BookingDto getOne(Long userId, Long bookingId) {
        userRepository.containsById(userId);
        return DtoBookingMapper.bookingToDtoWithoutItems(bookingRepository.getOne(bookingId));
    }

    public List<BookingDto> getByItemIdAndStatus(Long itemId, BookingStatus status) {
        return bookingRepository.getByItemIdAndStatus(itemId, status).stream().map(DtoBookingMapper::bookingToDto)
                .collect(Collectors.toList());
    }

    public ReviewDto addReview(Long requesterId, Long bookingId, ReviewDto reviewDto) {
        userRepository.containsById(requesterId);
        return DtoReviewMapper.reviewToDto(bookingRepository.addReview(requesterId, bookingId, DtoReviewMapper.dtoToReview(reviewDto)));
    }

    public ReviewDto updateReview(Long requesterId, Long bookingId, ReviewDto reviewDto) {
        userRepository.containsById(requesterId);
        return DtoReviewMapper.reviewToDto(bookingRepository.updateReview(requesterId, bookingId, reviewDto));
    }

    public void deleteReview(Long requesterId, Long reviewId) {
        userRepository.containsById(requesterId);
        bookingRepository.deleteReview(requesterId, reviewId);
    }

    public List<ReviewDto> getReviewsForItem(Long userId, Long itemId) {
        userRepository.containsById(userId);
        return bookingRepository.getReviewsForItem(itemId).stream().map(DtoReviewMapper::reviewToDtoWithoutUsers)
                .collect(Collectors.toList());
    }

    private void checkBookingTime(BookingDto booking) {
        if (booking.getStart().isAfter(booking.getEnd())) {
            throw new ValidationException("время введено неверно");
        }
    }
}
