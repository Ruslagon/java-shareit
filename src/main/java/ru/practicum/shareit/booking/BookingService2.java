package ru.practicum.shareit.booking;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.DtoBookingMapper;
import ru.practicum.shareit.booking.model.QBooking;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.exception.model.BadRequest;
import ru.practicum.shareit.item.ItemRepository2;
import ru.practicum.shareit.user.UserRepository2;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class BookingService2 {
    UserRepository2 userRepository;
    ItemRepository2 itemRepository;

    BookingRepository2 bookingRepository;
    //JPAQueryFactory queryFactory;

//    public BookingService2(UserRepository2 userRepository, ItemRepository2 itemRepository, BookingRepository2 bookingRepository, EntityManager entityManager) {
//        this.userRepository = userRepository;
//        this.itemRepository = itemRepository;
//        this.bookingRepository = bookingRepository;
//        this.queryFactory = new JPAQueryFactory(entityManager);
//    }

    @Transactional
    public BookingDto add(Long userId, BookingDto bookingDto) {
        checkBookingTime(bookingDto);
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user по id - " + userId + " не найден"));

        var item = itemRepository.findById(bookingDto.getItemId())
                .orElseThrow(() -> new EntityNotFoundException("item по id - " + bookingDto.getItemId() + " не найден"));
        if (!item.getAvailable()) {
            throw new BadRequest("предмет заблокирован. itemId - " + item.getId());
        }
        if (item.getOwner().getId().equals(userId)) {
            throw new EntityNotFoundException("user по id - " + userId + " не найден");
        }
        var booking = DtoBookingMapper.dtoToBooking(bookingDto);
        booking.setStatus(BookingStatus.WAITING);
        booking.setItem(item);
        booking.setBooker(user);
        //booking.setItemOwnerId(item.getOwnerId());
//        System.out.println(booking);
        var bok2 = bookingRepository.save(booking);
//        System.out.println(bok2);
        return DtoBookingMapper.bookingToDto(bok2);
    }

    @Transactional
    public BookingDto update(Long userId, Long bookingId, Boolean approved) {
        var booking = bookingRepository.findByIdAndOwnerId(bookingId, userId)
                .orElseThrow(() -> new EntityNotFoundException("user по id - " + userId + " не найден"));
        if (approved) {
            if (booking.getStatus().equals(BookingStatus.APPROVED)) {
                throw new BadRequest("не нужный запрос");
            }
            booking.setStatus(BookingStatus.APPROVED);
        } else if (booking.getStatus().equals(BookingStatus.APPROVED)) {
            booking.setStatus(BookingStatus.CANCELED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        return DtoBookingMapper.bookingToDto(bookingRepository.save(booking));
    }

    @Transactional
    public void delete(Long requesterId, Long bookingId) {
        bookingRepository.findByIdAndOwnerOrBooker(bookingId, requesterId)
                .orElseThrow(() -> new ConflictException("вы не владеете правом на удаление"));
        bookingRepository.deleteById(bookingId);
    }

    public List<BookingDto> getBookersBookings(Long bookerId, String state) {
        userRepository.findById(bookerId).orElseThrow(() -> new EntityNotFoundException("user по id - " + bookerId + " не найден"));
        LocalDateTime now = LocalDateTime.now();
        QBooking booking = QBooking.booking;
        BooleanExpression byBookerId = booking.booker.id.eq(bookerId);
        var sort2 = new QSort(booking.end.desc());
        PageRequest pageRequest = PageRequest.of(0, 50, sort2);
        if (BookingState.ALL.name().equals(state)) {
            return bookingRepository.findAll(byBookerId, pageRequest).stream().map(DtoBookingMapper::bookingToDto)
                    .collect(Collectors.toList());
            //return bookingRepository.findAllByOwnerIdByStartAsc(ownerId).stream().map(DtoBookingMapper::bookingToDto).collect(Collectors.toList());
        } else if (BookingState.CURRENT.name().equals(state)) {
            BooleanExpression startBeforeNow = booking.start.before(now);
            BooleanExpression endAfterNow = booking.end.after(now);
            //bookingRepository.findAll(byBookerId.and(startBeforeNow.and(endAfterNow)));
//            return queryFactory.selectFrom(booking).where(byBookerId.and(startBeforeNow.and(endAfterNow))).orderBy(booking.start.asc())
//                    .stream().map(DtoBookingMapper::bookingToDto).collect(Collectors.toList());
            return bookingRepository.findAll(byBookerId.and(startBeforeNow.and(endAfterNow)), pageRequest).stream()
                    .map(DtoBookingMapper::bookingToDto).collect(Collectors.toList());
        } else if (BookingState.FUTURE.name().equals(state)) {
            Predicate startAfterNow = booking.start.after(now);
            return bookingRepository.findAll(byBookerId.and(startAfterNow), pageRequest).stream().map(DtoBookingMapper::bookingToDto)
                    .collect(Collectors.toList());
        } else if (BookingState.PAST.name().equals(state)) {
            BooleanExpression endBeforeNow = booking.end.before(now);
            return bookingRepository.findAll(byBookerId.and(endBeforeNow), pageRequest).stream().map(DtoBookingMapper::bookingToDto)
                    .collect(Collectors.toList());
        } else if (BookingState.REJECTED.name().equals(state)) {
            BooleanExpression statusIsRejected = booking.status.eq(BookingStatus.REJECTED).or(booking.status.eq(BookingStatus.CANCELED));
            return bookingRepository.findAll(byBookerId.and(statusIsRejected), pageRequest).stream().map(DtoBookingMapper::bookingToDto)
                    .collect(Collectors.toList());
        } else if (BookingState.WAITING.name().equals(state)) {
            BooleanExpression statusIsWaiting = booking.status.eq(BookingStatus.WAITING);
            return bookingRepository.findAll(byBookerId.and(statusIsWaiting), pageRequest).stream().map(DtoBookingMapper::bookingToDto)
                    .collect(Collectors.toList());
        }
//        return bookingRepository.findAllByUsersIdAndByState(ownerId).stream().map(DtoBookingMapper::bookingToDto)
//                .collect(Collectors.toList());
        throw new BadRequest("Unknown state: " + state);
    }

    public BookingDto getOne(Long userId, Long bookingId) {
        return DtoBookingMapper.bookingToDto(bookingRepository.findByIdAndOwnerOrBooker(bookingId, userId)
                .orElseThrow(() -> new EntityNotFoundException("аренда не доступна " + bookingId)));
//        return DtoBookingMapper.bookingToDtoWithoutUsers(bookingRepository.getOne(bookingId));
    }

    public List<BookingDto> getOwnersBookings(Long ownerId, String state) {
        System.out.println(state);
        userRepository.findById(ownerId).orElseThrow(() -> new EntityNotFoundException("user по id - " + ownerId + " не найден"));
        LocalDateTime now = LocalDateTime.now();
        QBooking booking = QBooking.booking;
        BooleanExpression byOwnerId = booking.item.owner.id.eq(ownerId);
        var sort2 = new QSort(booking.end.desc());
        PageRequest pageRequest = PageRequest.of(0, 50, sort2);
        if (BookingState.ALL.name().equals(state)) {
            return bookingRepository.findAll(byOwnerId, pageRequest).stream().map(DtoBookingMapper::bookingToDto)
                    .collect(Collectors.toList());
            //return bookingRepository.findAllByOwnerIdByStartAsc(ownerId).stream().map(DtoBookingMapper::bookingToDto).collect(Collectors.toList());
        } else if (BookingState.CURRENT.name().equals(state)) {
            BooleanExpression startBeforeNow = booking.start.before(now);
            BooleanExpression endAfterNow = booking.end.after(now);
            //bookingRepository.findAll(byOwnerId.and(startBeforeNow.and(endAfterNow)));
//            return queryFactory.selectFrom(booking).where(byOwnerId.and(startBeforeNow.and(endAfterNow))).orderBy(booking.start.asc())
//                    .stream().map(DtoBookingMapper::bookingToDto).collect(Collectors.toList());
            return bookingRepository.findAll(byOwnerId.and(startBeforeNow.and(endAfterNow)), pageRequest).stream()
                    .map(DtoBookingMapper::bookingToDto).collect(Collectors.toList());
        } else if (BookingState.FUTURE.name().equals(state)) {
            Predicate startAfterNow = booking.start.after(now);
            return bookingRepository.findAll(byOwnerId.and(startAfterNow), pageRequest).stream().map(DtoBookingMapper::bookingToDto)
                    .collect(Collectors.toList());
        } else if (BookingState.PAST.name().equals(state)) {
            BooleanExpression endBeforeNow = booking.end.before(now);
            return bookingRepository.findAll(byOwnerId.and(endBeforeNow), pageRequest).stream().map(DtoBookingMapper::bookingToDto)
                    .collect(Collectors.toList());
        } else if (BookingState.REJECTED.name().equals(state)) {
            BooleanExpression statusIsRejected = booking.status.eq(BookingStatus.REJECTED).or(booking.status.eq(BookingStatus.CANCELED));
            return bookingRepository.findAll(byOwnerId.and(statusIsRejected), pageRequest).stream().map(DtoBookingMapper::bookingToDto)
                    .collect(Collectors.toList());
        } else if (BookingState.WAITING.name().equals(state)) {
            BooleanExpression statusIsWaiting = booking.status.eq(BookingStatus.WAITING);
            return bookingRepository.findAll(byOwnerId.and(statusIsWaiting), pageRequest).stream().map(DtoBookingMapper::bookingToDto)
                    .collect(Collectors.toList());
        }
//        return bookingRepository.findAllByUsersIdAndByState(ownerId).stream().map(DtoBookingMapper::bookingToDto)
//                .collect(Collectors.toList());
        throw new BadRequest("Unknown state: " + state);
    }

//    public List<BookingDto> getByItemIdAndStatus(Long itemId, BookingStatus status) {
//        return null;
////        bookingRepository.getByItemIdAndStatus(itemId, status).stream().map(DtoBookingMapper::bookingToDto)
////                .collect(Collectors.toList());
//    }

//    public ReviewDto addReview(Long requesterId, Long bookingId, ReviewDto reviewDto) {
//        userRepository.containsById(requesterId);
//        return DtoReviewMapper.reviewToDto(bookingRepository.addReview(requesterId, bookingId, DtoReviewMapper.dtoToReview(reviewDto)));
//    }
//
//    public ReviewDto updateReview(Long requesterId, Long bookingId, ReviewDto reviewDto) {
//        userRepository.containsById(requesterId);
//        return DtoReviewMapper.reviewToDto(bookingRepository.updateReview(requesterId, bookingId, reviewDto));
//    }
//
//    public void deleteReview(Long requesterId, Long reviewId) {
//        userRepository.containsById(requesterId);
//        bookingRepository.deleteReview(requesterId, reviewId);
//    }
//
//    public List<ReviewDto> getReviewsForItem(Long userId, Long itemId) {
//        userRepository.containsById(userId);
//        return bookingRepository.getReviewsForItem(itemId).stream().map(DtoReviewMapper::reviewToDtoWithoutUsers)
//                .collect(Collectors.toList());
//    }

    private void checkBookingTime(BookingDto booking) {
        if (booking.getStart().isAfter(booking.getEnd()) || booking.getStart().isEqual(booking.getEnd())) {
            throw new BadRequest("время введено неверно");
        }
    }
}
