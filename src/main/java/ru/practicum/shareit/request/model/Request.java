package ru.practicum.shareit.request.model;

import lombok.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "requests")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String description;

    private LocalDateTime created;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = false, cascade = CascadeType.REMOVE, mappedBy = "request")
    @Column(nullable = true, insertable = false, updatable = false)
    private List<Item> items;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}