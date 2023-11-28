package ru.practicum.shareit.item.model;

import lombok.*;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * TODO Sprint add-controllers.
 */

@Entity
@Table(name = "items")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @ToString.Exclude
    private User owner;

//    @Column(name = "request_id")
//    private Long requestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    @ToString.Exclude
    private Request request;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean available;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = false, cascade = CascadeType.REMOVE, mappedBy = "item")
    @Column(nullable = true, insertable = false, updatable = false)
    private List<Booking> bookings;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = false, cascade = CascadeType.REMOVE, mappedBy = "item")
    @Column(nullable = true, insertable = false, updatable = false)
    private List<Comment> comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
