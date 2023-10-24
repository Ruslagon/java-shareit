package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.DtoUserMapper2;
import ru.practicum.shareit.user.model.User;

public class DtoBookingMapper {

    public static Booking dtoToBooking(BookingDto bookingDto) {
        Booking booking = new Booking();
        booking.setId(bookingDto.getId());
        booking.setItem(bookingDto.getItem());
        //booking.setItemOwnerId(bookingDto.getItemOwnerId());
        booking.setBooker(bookingDto.getBooker());
        booking.setStatus(bookingDto.getStatus());
        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        return booking;
    }

    public static BookingDto bookingToDto(Booking booking) {
        //DtoUserMapper2 dtoUserMapper2;
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setItem(new Item());
        bookingDto.getItem().setId(booking.getItem().getId());
        bookingDto.getItem().setName(booking.getItem().getName());
        bookingDto.setBooker(new User());
        bookingDto.getBooker().setId(booking.getBooker().getId());
        //bookingDto.setItemOwnerId(booking.getItemOwnerId());
        //bookingDto.setBooker(booking.getBooker());
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
        //bookingDto.setItemId(booking.getItemId());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setStart(booking.getStart());
        bookingDto.setEnd(booking.getEnd());
        return bookingDto;
    }
}
