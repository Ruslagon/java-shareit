package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;

public class DtoItemMapper {

    public static Item DtoToItem(ItemDto itemDto) {
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setRequestId(itemDto.getRequestId());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        item.setName(itemDto.getName());
        item.setOwnerId(itemDto.getOwnerId());
        return item;
    }

    public static ItemDto ItemToDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setRequestId(item.getRequestId());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        itemDto.setName(item.getName());
        itemDto.setOwnerId(item.getOwnerId());
        return itemDto;
    }

    public static ItemDto itemToDtoWithoutUsers(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        itemDto.setRequestId(item.getRequestId());
        return itemDto;
    }
}
