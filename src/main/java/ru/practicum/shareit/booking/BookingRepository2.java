package ru.practicum.shareit.booking;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingInfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public interface BookingRepository2 extends JpaRepository<Booking, Long>, QuerydslPredicateExecutor<Booking> {
    @Query("select book " +
            "from Booking as book " +
            "JOIN FETCH book.booker as ber " +
            "JOIN FETCH book.item as it " +
            "JOIN it.owner as ow " +
            "where book.id = ?1 " +
            "and ber.id = ?2 " +
            "or book.id = ?1 " +
            "and ow.id = ?2 ")
    Optional<Booking> findByIdAndOwnerOrBooker(Long id, Long userId);

    @Query("select book " +
            "from Booking as book " +
            "JOIN FETCH book.booker as ber " +
            "JOIN FETCH book.item as it " +
            "JOIN it.owner as ow " +
            "where book.id = ?1 " +
            "and ow.id = ?2 ")
    Optional<Booking> findByIdAndOwnerId(Long id, Long userId);

//    List<Booking> findAllByOwnerIdOrderByStartAsc(Long ownerId);

    List<Booking> findAllByOrderByStartAsc();
    List<Booking> findAllByItemId(Long itemId);

    Booking findFirstByItemIdAndStartBeforeOrderByStartDesc(Long itemId, LocalDateTime now);
    Booking findFirstByItemIdAndStartAfterAndStatusOrderByStartAsc(Long itemId, LocalDateTime now, BookingStatus Status);
    //findByItemIdAndEndBeforeByOrderByEndAsc

    Page<BookingInfo> findAll(Predicate predicate, PageRequest pageRequest);
}
