package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewDto {
    Long id;
    Long clientId;
    Long reviewedItemId;
    Long bookingId;
    @NotNull
    Boolean positive;
    @NotNull
    String opinion;
    LocalDateTime reviewTime = LocalDateTime.now();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewDto reviewDto = (ReviewDto) o;
        return Objects.equals(id, reviewDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
