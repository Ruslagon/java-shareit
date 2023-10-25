package ru.practicum.shareit.booking.model;

import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.model.ItemBookingsInfo;
import ru.practicum.shareit.user.model.UserInfoId;

import java.time.LocalDateTime;

public interface BookingInfo {
        Long getId();

        ItemBookingsInfo getItem();

        UserInfoId getBooker();

        BookingStatus getStatus();

        LocalDateTime getStart();

        LocalDateTime getEnd();
}
