package ru.practicum.shareit.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exception.model.BadRequest;

public class EntityNotFoundExceptionTest {

    @Test
    void EntityNotFoundExceptionThrow() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> {throw new EntityNotFoundException();});
        Assertions.assertThrows(EntityNotFoundException.class, () -> {throw new EntityNotFoundException("EntityNotFoundException");});
    }
}
