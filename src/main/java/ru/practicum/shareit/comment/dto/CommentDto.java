package ru.practicum.shareit.comment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    Long id;
    @NotBlank
    String text;
    LocalDateTime created = LocalDateTime.now();
    String authorName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentDto that = (CommentDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
