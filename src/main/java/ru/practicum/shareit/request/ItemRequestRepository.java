package ru.practicum.shareit.request;

import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

public interface ItemRequestRepository {
    ItemRequest add(Long userId, ItemRequest request);

    void containsById(Long requestId);

    void containsSameOwner(Long userId, Long requestId);

    ItemRequest update(ItemRequest request, Long requestId);

    List<ItemRequest> getAllForUser(Long userId);

    ItemRequest getOneWithOutOwner(Long requestId);

    List<ItemRequest> search(String text);

    void delete(Long requestId);

    List<Long> deleteByUserIdAndGetDeletedIds(Long userId);
}
