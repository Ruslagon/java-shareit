package ru.practicum.shareit.request;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.dto.DtoRequestMapper;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public ItemRequestService(ItemRequestRepository itemRequestRepository, UserRepository userRepository,
                              ItemRepository itemRepository) {
        this.itemRequestRepository = itemRequestRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public ItemRequestDto add(Long userId, ItemRequestDto request) {
        userRepository.containsById(userId);
        return DtoRequestMapper.ItemRequestToDto(itemRequestRepository.add(userId, DtoRequestMapper.DtoToItemRequest(request)));
    }

    public ItemRequestDto update(Long userId, ItemRequestDto requestDto, Long requestId) {
        userRepository.containsById(userId);
        containsSameOwner(userId, requestId);
        return DtoRequestMapper.ItemRequestToDto(itemRequestRepository.update(DtoRequestMapper.DtoToItemRequest(requestDto), requestId));
    }
    public void containsSameOwner(Long userId, Long requestId) {
        itemRequestRepository.containsSameOwner(userId, requestId);
    }

    public List<ItemRequestDto> getAllForUser(Long userId) {
        userRepository.containsById(userId);
        return itemRequestRepository.getAllForUser(userId).stream().map(DtoRequestMapper::ItemRequestToDto).collect(Collectors.toList());
    }

    public ItemRequestDto getOne(Long userId, Long requestId) {
        userRepository.containsById(userId);
        return DtoRequestMapper.itemRequestToDtoWithoutUsers(itemRequestRepository.getOneWithOutOwner(requestId));
    }

    public List<ItemRequestDto> search(Long userId, String text) {
        userRepository.containsById(userId);
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return itemRequestRepository.search(text.toLowerCase()).stream()
                .map(DtoRequestMapper::itemRequestToDtoWithoutUsers).collect(Collectors.toList());
    }

    public void delete(Long userId, Long requestId) {
        userRepository.containsById(userId);
        containsSameOwner(userId, requestId);
        itemRequestRepository.delete(requestId);
        List<Long> requestList = new ArrayList<>();
        requestList.add(requestId);
        itemRepository.clearFromDeletedRequests(requestList);
    }
}