package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.validation.Marker;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RestController
@Validated
@RequestMapping(path = "/users")
public class UserController {

    private final UserService2 userService;

    public UserController(UserService2 userService) {
        this.userService = userService;
    }

    @PostMapping
    @Validated({Marker.OnCreate.class})
    public UserDto add(@Valid @RequestBody UserDto userToAdd) {
        log.info("добавить пользователя user={}", userToAdd);
        return userService.add(userToAdd);
    }

    @PatchMapping("/{userId}")
    @Validated({Marker.OnUpdate.class})
    public UserDto update(@Valid @RequestBody UserDto userToUpdate, @PathVariable Long userId) {
        log.info("обновить юзера с id{}", userId);
        log.info("данные для обновления={}", userToUpdate);
        return userService.update(userToUpdate, userId);
    }

    @GetMapping
    public List<UserDto> getAll() {
        log.info("получить всех юзеров");
        return userService.getAll();
    }

    @GetMapping("/{userId}")
    public UserDto getOne(@PathVariable Long userId) {
        log.info("получить юзера с id{}", userId);
        return userService.getOne(userId);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        log.info("удалить юзера с id{}", userId);
        userService.delete(userId);
    }
}
