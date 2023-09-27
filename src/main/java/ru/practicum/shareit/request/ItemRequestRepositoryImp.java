package ru.practicum.shareit.request;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ItemRequestRepositoryImp implements ItemRequestRepository {
    private final Map<Long, ItemRequest> requestsMap = new HashMap<>();
    private Long globalId = 1L;

    @Override
    public ItemRequest add(Long userId, ItemRequest request) {
        request.setId(globalId);
        request.setRequesterId(userId);
        requestsMap.put(globalId, request);
        globalId++;
        return request;
    }

    @Override
    public void containsById(Long requestId) {
        if (requestsMap.containsKey(requestId)) {
            return;
        }
        throw new EntityNotFoundException("request по id - " + requestId + " не найден");
    }

    @Override
    public void containsSameOwner(Long userId, Long requestId) {
        Long ownerId = getRequest(requestId).getRequesterId();
        if (!ownerId.equals(userId)) {
            throw new EntityNotFoundException("user - " + userId + " не владеет request - " + requestId);
        }
    }

    @Override
    public ItemRequest update(ItemRequest request, Long requestId) {
        ItemRequest oldRequest = getRequest(requestId);
        if (request.getDescription() != null) {
            oldRequest.setDescription(request.getDescription());
        }
        if (request.getResolved() != null) {
            oldRequest.setResolved(request.getResolved());
        }
        return oldRequest;
    }

    @Override
    public List<ItemRequest> getAllForUser(Long userId) {
        return requestsMap.values().stream()
                .filter(request -> request.getRequesterId().equals(userId)).collect(Collectors.toList());
    }

    @Override
    public ItemRequest getOneWithOutOwner(Long requestId) {
        containsById(requestId);
        return getRequest(requestId);
    }

    @Override
    public List<ItemRequest> search(String text) {
        return requestsMap.values().stream()
                .filter(request -> request.getDescription().toLowerCase().contains(text))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long requestId) {
        requestsMap.remove(requestId);
    }

    @Override
    public List<Long> deleteByUserIdAndGetDeletedIds(Long userId) {
        List<Long> deletedIds = new ArrayList<>();
        requestsMap.values().removeIf(request -> {
            if (request.getRequesterId().equals(userId)) {
                deletedIds.add(request.getId());
                return true;
            }
            return false;
        });
        return deletedIds;
    }

    private ItemRequest getRequest(Long requestId) {
        containsById(requestId);
        return requestsMap.get(requestId);
    }
}
