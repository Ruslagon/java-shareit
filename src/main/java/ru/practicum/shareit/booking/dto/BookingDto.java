package ru.practicum.shareit.booking.dto;

import lombok.Data;
import ru.practicum.shareit.booking.BookingStatus;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
public class BookingDto {
    Long id;
    @NotNull
    Long itemId;
    @Future
    LocalDateTime start;
    @Future
    LocalDateTime end;
    BookingStatus status;
}
