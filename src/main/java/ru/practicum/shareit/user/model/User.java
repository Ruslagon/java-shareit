package ru.practicum.shareit.user.model;

import lombok.*;

import java.util.Objects;

/**
 * TODO Sprint add-controllers.
 */

@Getter
@Setter
@ToString
public class User {
    private Long id;

    private String name;

    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
