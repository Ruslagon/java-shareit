package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.user.dto.DtoUserMapper;
import ru.practicum.shareit.user.dto.DtoUserMapper2;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserService2 {
    @Autowired
    private final UserRepository2 userRepository;
//    @Autowired
//    private final DtoUserMapper2 DtoUserMapper;

    @Transactional
    public UserDto add(UserDto userToAdd) {
        userToAdd.setId(null);
        return DtoUserMapper.userToDto(userRepository.save(DtoUserMapper.dtoToUser(userToAdd)));
    }

    @Transactional
    public UserDto update(UserDto userToUpdate, Long userId) {
        //containsById(userId);
        userToUpdate.setId(userId);
        var oldUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user по id - " + userId + " не найден"));
        DtoUserMapper.updateUserFromDto(userToUpdate, oldUser);
        return DtoUserMapper.userToDto(userRepository.save(oldUser));
    }

    public List<UserDto> getAll() {
//        return userRepository.findAll().stream().map(DtoUserMapper::userToDto).collect(Collectors.toList());
//        var sort2 = new QSort(booking.end.desc());
        PageRequest pageRequest = PageRequest.of(0, 50);
//        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        return userRepository.findAll(pageRequest).map(DtoUserMapper::userToDto)
                .getContent();
    }

    public UserDto getOne(Long userId) {
        return DtoUserMapper.userToDto(userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user по id - " + userId + " не найден")));
    }

    @Transactional
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

//    public void containsById(Long userId) {
//        userRepository.findById(userId)
//                .orElseThrow(() -> new EntityNotFoundException("user по id - " + userId + " не найден"));
//    }
}
