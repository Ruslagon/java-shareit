package ru.practicum.shareit.item;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.item.dto.DtoItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    private final ItemRequestRepository itemRequestRepository;
    private final BookingRepository bookingRepository;


    public ItemService(ItemRepository itemRepository, UserRepository userRepository,
                       ItemRequestRepository itemRequestRepository, BookingRepository bookingRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.itemRequestRepository = itemRequestRepository;
        this.bookingRepository = bookingRepository;
    }

    public ItemDto add(Long userId, ItemDto itemDto) {
        userRepository.containsById(userId);
        if (itemDto.getRequestId() != null) {
            itemRequestRepository.containsById(itemDto.getRequestId());
        }
        return DtoItemMapper.ItemToDto(itemRepository.add(userId, DtoItemMapper.DtoToItem(itemDto)));
    }

    public ItemDto update(Long userId, ItemDto itemDto, Long itemId) {
        containsSameOwner(userId, itemId);
        return DtoItemMapper.ItemToDto(itemRepository.update(DtoItemMapper.DtoToItem(itemDto), itemId));
    }
    public void containsSameOwner(Long userId, Long itemId) {
        itemRepository.containsSameOwner(userId, itemId);
    }

    public List<ItemDto> getAllForUser(Long userId) {
        userRepository.containsById(userId);
        return itemRepository.getAllForUser(userId).stream().map(DtoItemMapper::ItemToDto).collect(Collectors.toList());
    }

    public ItemDto getOne(Long userId, Long itemId) {
        userRepository.containsById(userId);
        return DtoItemMapper.itemToDtoWithoutUsers(itemRepository.getOneWithoutOwner(itemId));
    }

    public List<ItemDto> search(Long userId, String text) {
        userRepository.containsById(userId);
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return itemRepository.search(text.toLowerCase()).stream().map(DtoItemMapper::itemToDtoWithoutUsers)
                .collect(Collectors.toList());
    }

    public void delete(Long userId, Long itemId) {
        containsSameOwner(userId, itemId);
        itemRepository.delete(itemId);
        bookingRepository.deleteByItemId(itemId);
    }
}
