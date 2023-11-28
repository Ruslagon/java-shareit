package ru.practicum.shareit.item.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemInfo;

@UtilityClass
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

    public static Item updateItemFromDto(ItemDto itemDto, Item item) {
        if (itemDto == null) {
            return item;
        }

        if (itemDto.getRequestId() != null) {
            item.setRequestId(itemDto.getRequestId());
        }
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }

        return item;
    }
}
