package ru.practicum.shareit.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@Slf4j
@RestController
@Validated
@RequestMapping(path = "/requests")
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    public ItemRequestController(ItemRequestService itemRequestService) {
        this.itemRequestService = itemRequestService;
    }

    @PostMapping
    public ItemRequestDto add(@RequestHeader("X-Sharer-User-Id") Long userId, @Valid @RequestBody ItemRequestDto requestDto) {
        log.info("добавить для пользователя userId={}", userId);
        log.info("request для добавления={}", requestDto);
        return itemRequestService.add(userId, requestDto);
    }

    @PatchMapping("/{requestId}")
    public ItemRequestDto update(@RequestHeader("X-Sharer-User-Id") Long userId,
                                 @RequestBody ItemRequestDto requestDto, @PathVariable Long requestId) {
        log.info("изменить для пользователя userId={} данные requestId={}", userId, requestId);
        log.info("данные для изменения={}", requestDto);
        return itemRequestService.update(userId, requestDto, requestId);
    }

    @GetMapping
    public List<ItemRequestDto> getAllForUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("получить все запросы от пользователя userId={}", userId);
        return itemRequestService.getAllForUser(userId);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getOne(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long requestId) {
        log.info("получить запрос для itemId={}", requestId);
        return itemRequestService.getOne(userId, requestId);
    }

    @GetMapping("/search")
    public List<ItemRequestDto> search(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestParam String text) {
        log.info("получить запросы по тексту={}", text);
        return itemRequestService.search(userId, text);
    }

    @DeleteMapping("/{requestId}")
    public void delete(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long requestId) {
        log.info("удалить запрос с id={}", requestId);
        itemRequestService.delete(userId, requestId);
    }
}
