package ru.practicum.shareit.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exception.model.BadRequest;

public class ConflictExceptionTest {

    @Test
    void ConflictExceptionThrow() {
        Assertions.assertThrows(ConflictException.class, () -> {throw new ConflictException("ConflictException");});
    }
}
