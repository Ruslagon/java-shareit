package ru.practicum.shareit.item.dto;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemInfo;

public class DtoItemMapper {

    public static Item dtoToItem(ItemDto itemDto) {
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setRequestId(itemDto.getRequestId());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        item.setName(itemDto.getName());
        item.setOwner(itemDto.getOwner());
        return item;
    }

    public static ItemDto itemToDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setRequestId(item.getRequestId());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        itemDto.setName(item.getName());
        itemDto.setOwner(item.getOwner());
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

    public static ItemInfoDto itemInfoToDtoWithoutUsers(ItemInfo itemInfo) {
        ItemInfoDto itemDto = new ItemInfoDto();
        itemDto.setId(itemInfo.getId());
        itemDto.setName(itemInfo.getName());
        itemDto.setDescription(itemInfo.getDescription());
        itemDto.setAvailable(itemInfo.getAvailable());
        itemDto.setRequestId(itemInfo.getRequestId());
        itemDto.setComments(itemInfo.getComments());
        return itemDto;
    }
}
