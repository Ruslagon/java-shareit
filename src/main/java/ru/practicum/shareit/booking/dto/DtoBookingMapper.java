package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.Booking;

public class DtoBookingMapper {

    public static Booking dtoToBooking(BookingDto bookingDto) {
        Booking booking = new Booking();
        booking.setId(bookingDto.getId());
        booking.setItemId(bookingDto.getItemId());
        booking.setItemOwnerId(bookingDto.getItemOwnerId());
        booking.setBookerId(bookingDto.getBookerId());
        booking.setStatus(bookingDto.getStatus());
        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        return booking;
    }

    public static BookingDto bookingToDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setItemId(booking.getItemId());
        bookingDto.setItemOwnerId(booking.getItemOwnerId());
        bookingDto.setBookerId(booking.getBookerId());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setStart(booking.getStart());
        bookingDto.setEnd(booking.getEnd());
        return bookingDto;
    }

    public static BookingDto bookingToDtoWithoutUsers(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setItemId(booking.getItemId());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setStart(booking.getStart());
        bookingDto.setEnd(booking.getEnd());
        return bookingDto;
    }
}
