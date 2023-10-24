package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.comment.dto.CommentTest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.validation.Marker;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ItemInfoDto {
    private Long id;

    private User owner;

    private Long requestId;

    private String name;

    private String description;

    private Boolean available;

    @JsonInclude
    BookingDto lastBooking;

    @JsonInclude
    BookingDto nextBooking;

    @JsonInclude
    List<CommentTest> comments = new ArrayList<>();
}
