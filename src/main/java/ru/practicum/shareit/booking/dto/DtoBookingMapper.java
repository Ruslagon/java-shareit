package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.ItemBookingsInfo;
import ru.practicum.shareit.user.model.UserInfoId;

public class DtoBookingMapper {

    public static Booking dtoToBooking(BookingDto bookingDto) {
        Booking booking = new Booking();
        booking.setId(bookingDto.getId());
        booking.setStatus(bookingDto.getStatus());
        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        return booking;
    }

    public static BookingInfoDto bookingToDto(Booking booking) {
        BookingInfoDto bookingDto = new BookingInfoDto();
        bookingDto.setId(booking.getId());
        bookingDto.setItem(new ItemBookingsInfo());
        bookingDto.getItem().setId(booking.getItem().getId());
        bookingDto.getItem().setName(booking.getItem().getName());
        bookingDto.setBooker(new UserInfoId());
        bookingDto.getBooker().setId(booking.getBooker().getId());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setStart(booking.getStart());
        bookingDto.setEnd(booking.getEnd());
        return bookingDto;
    }

    public static BookingDto bookingToDtoWithoutItems(Booking booking) {
        if (booking == null) {
            return null;
        }
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setBookerId(booking.getBooker().getId());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setStart(booking.getStart());
        bookingDto.setEnd(booking.getEnd());
        return bookingDto;
    }
}
