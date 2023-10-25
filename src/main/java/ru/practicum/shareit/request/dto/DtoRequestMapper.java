package ru.practicum.shareit.request.dto;

import ru.practicum.shareit.request.model.ItemRequest;

public class DtoRequestMapper {

    public static ItemRequest dtoToItemRequest(ItemRequestDto itemRequestDto) {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setId(itemRequestDto.getId());
        itemRequest.setCreated(itemRequestDto.getCreated());
        itemRequest.setResolved(itemRequestDto.getResolved());
        itemRequest.setRequesterId(itemRequestDto.getRequesterId());
        itemRequest.setDescription(itemRequestDto.getDescription());
        return itemRequest;
    }

    public static ItemRequestDto itemRequestToDto(ItemRequest itemRequest) {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(itemRequest.getId());
        itemRequestDto.setCreated(itemRequest.getCreated());
        itemRequestDto.setResolved(itemRequest.getResolved());
        itemRequestDto.setRequesterId(itemRequest.getRequesterId());
        itemRequestDto.setDescription(itemRequest.getDescription());
        return itemRequestDto;
    }

    public static ItemRequestDto itemRequestToDtoWithoutUsers(ItemRequest itemRequest) {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(itemRequest.getId());
        itemRequestDto.setCreated(itemRequest.getCreated());
        itemRequestDto.setResolved(itemRequest.getResolved());
        itemRequestDto.setDescription(itemRequest.getDescription());
        return itemRequestDto;
    }
}
