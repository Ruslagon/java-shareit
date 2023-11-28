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
import java.util.Objects;

/**
 * TODO Sprint add-controllers.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Long id;

    private User owner;

    private Long requestId;

    @NotBlank(groups = Marker.OnCreate.class)
    private String name;

    @NotBlank(groups = Marker.OnCreate.class)
    private String description;

    @NotNull(groups = Marker.OnCreate.class)
    private Boolean available;

    @JsonInclude
    BookingDto lastBooking;
    @JsonInclude
    BookingDto nextBooking;

    @JsonInclude
    List<CommentTest> comments = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDto itemDto = (ItemDto) o;
        return Objects.equals(id, itemDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
