package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {

    Item add(Long userId, Item item);

    Item update(Item item, Long itemId);

    List<Item> getAllForUser(Long userId);

    Item getOneWithoutOwner(Long itemId);

    List<Item> search(String text);

    void delete(Long itemId);

    void containsSameOwner(Long userId, Long itemId);

    void containsById(Long itemId);

    Item getOne(Long itemId);

    void clearFromDeletedRequests(List<Long> reqIds);

    void deleteByUserId(Long userId);
}
