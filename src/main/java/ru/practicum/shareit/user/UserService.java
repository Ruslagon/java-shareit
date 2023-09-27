package ru.practicum.shareit.user;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.user.dto.DtoUserMapper;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ItemRequestRepository itemRequestRepository;
    private final BookingRepository bookingRepository;

    public UserService(UserRepository userRepository, ItemRepository itemRepository,
                       ItemRequestRepository itemRequestRepository, BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.itemRequestRepository = itemRequestRepository;
        this.bookingRepository = bookingRepository;
    }

    public UserDto add(UserDto userToAdd) {
        if (userRepository.containsEmail(userToAdd.getEmail())) {
            throw new ConflictException("email - " + userToAdd.getEmail() + ", уже занят");
        }
        return DtoUserMapper.UserToDto(userRepository.add(DtoUserMapper.DtoToUser(userToAdd)));
    }

    public UserDto update(UserDto userToUpdate, Long userId) {
        containsById(userId);
        if (userToUpdate.getEmail() != null) {
            userRepository.containsEmailForUpdate(userToUpdate.getEmail(), userId);
        }
        return DtoUserMapper.UserToDto(userRepository.update(DtoUserMapper.DtoToUser(userToUpdate), userId));
    }

    public List<UserDto> getAll() {
        return userRepository.getAll().stream().map(DtoUserMapper::UserToDto).collect(Collectors.toList());
    }

    public UserDto getOne(Long userId) {
        containsById(userId);
        return DtoUserMapper.UserToDto(userRepository.getUser(userId));
    }

    public void delete(Long userId) {
        userRepository.delete(userId);
        var reqIds = itemRequestRepository.deleteByUserIdAndGetDeletedIds(userId);
        itemRepository.clearFromDeletedRequests(reqIds);
        itemRepository.deleteByUserId(userId);
        bookingRepository.deleteByUserId(userId);
    }

    public void containsById(Long userId) {
        userRepository.containsById(userId);
    }
}
