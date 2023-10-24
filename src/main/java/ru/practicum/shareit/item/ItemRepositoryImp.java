package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ItemRepositoryImp implements ItemRepository {
    private final Map<Long, Item> itemsMap = new HashMap<>();
    private Long globalId = 1L;


    @Override
    public Item add(Long userId, Item item) {
        //item.setOwnerId(userId);
        item.setId(globalId);
        itemsMap.put(globalId, item);
        globalId++;
        return item;
    }

    @Override
    public Item getOne(Long itemId) {
        containsById(itemId);
        return itemsMap.get(itemId);
    }

    @Override
    public Item update(Item item, Long itemId) {
        Item oldItemToUpdate = getOne(itemId);
        if (item.getName() != null) {
            oldItemToUpdate.setName(item.getName());
        }
        if (item.getDescription() != null) {
            oldItemToUpdate.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            oldItemToUpdate.setAvailable(item.getAvailable());
        }
        return oldItemToUpdate;
    }

    @Override
    public List<Item> getAllForUser(Long userId) {
        //return itemsMap.values().stream().filter(item -> item.getOwnerId().equals(userId)).collect(Collectors.toList());
    return null;
    }

    @Override
    public Item getOneWithoutOwner(Long itemId) {
        return getOne(itemId);
    }

    @Override
    public void containsSameOwner(Long userId, Long itemId) {
        //Long ownerId = getOne(itemId).getOwnerId();
//        if (!ownerId.equals(userId)) {
//            throw new EntityNotFoundException("user - " + userId + " не владеет item - " + itemId);
//        }
    }

    @Override
    public void containsById(Long itemId) {
        if (itemsMap.containsKey(itemId)) {
            return;
        }
        throw new EntityNotFoundException("item по id - " + itemId + " не найден");
    }

    @Override
    public List<Item> search(String text) {
        return itemsMap.values().stream().filter(Item::getAvailable)
                .filter(item -> item.getDescription().toLowerCase().contains(text) || item.getName().toLowerCase().contains(text))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long itemId) {
        itemsMap.remove(itemId);
    }

    @Override
    public void clearFromDeletedRequests(List<Long> reqIds) {
        for (Item item : itemsMap.values()) {
            if (item.getRequestId() != null && reqIds.contains(item.getRequestId())) {
                item.setRequestId(null);
            }
        }
    }

    @Override
    public void deleteByUserId(Long userId) {
        //itemsMap.values().removeIf(item -> item.getOwnerId().equals(userId));
    }
}
