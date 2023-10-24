package ru.practicum.shareit.booking.model;

import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.model.ItemBookingsInfo;
import ru.practicum.shareit.user.model.UserInfoId;

import java.time.LocalDateTime;

public interface BookingInfo {
        Long getId();
        ItemBookingsInfo getItem();
        UserInfoId getBooker();
//        bookingDto.setItem(new Item());
//        bookingDto.getItem().setId(booking.getItem().getId());
//        bookingDto.getItem().setName(booking.getItem().getName());
//        bookingDto.setBooker(new User());
//        bookingDto.getBooker().setId(booking.getBooker().getId());
    //bookingDto.setItemOwnerId(booking.getItemOwnerId());
    //bookingDto.setBooker(booking.getBooker());
        BookingStatus getStatus();
        LocalDateTime getStart();
        LocalDateTime getEnd();
}
