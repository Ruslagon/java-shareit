package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserRepositoryImp implements UserRepository {
    private final Map<Long, User> usersMap = new HashMap<>();
    private Long globalId = 1L;

    @Override
    public User add(User userToAdd) {
        userToAdd.setId(globalId);
        usersMap.put(globalId, userToAdd);
        globalId++;
        return userToAdd;
    }

    @Override
    public User update(User userToUpdate, Long userId) {
        User oldUserToUpdate = usersMap.get(userId);
        if (userToUpdate.getEmail() != null) {
            oldUserToUpdate.setEmail(userToUpdate.getEmail());
        }
        if (userToUpdate.getName() != null) {
            oldUserToUpdate.setName(userToUpdate.getName());
        }
        return oldUserToUpdate;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(usersMap.values());
    }

    @Override
    public void delete(Long userId) {
        usersMap.remove(userId);
    }

    @Override
    public boolean containsEmail(String email) {
        for (User olduser : usersMap.values()) {
            if (email.equals(olduser.getEmail())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void containsEmailForUpdate(String email,Long userId) {
        usersMap.values().stream().filter(user -> (email.equals(user.getEmail()) && !(user.getId().equals(userId)))).findFirst()
                .ifPresent((user) -> {
                    throw new ConflictException("email - " + email + ", уже занят");
                });
    }

    @Override
    public User getUser(Long userId) {
        return usersMap.get(userId);
    }

    @Override
    public void containsById(Long userId) {
        if (usersMap.containsKey(userId)) {
            return;
        }
        throw new EntityNotFoundException("user по id - " + userId + " не найден");
    }
}
