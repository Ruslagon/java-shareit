package ru.practicum.shareit.comment.dto;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface CommentTest {

    Long getId();

    String getText();

    LocalDateTime getCreated();

    @Value("#{target.author.name}")
    String getAuthorName();
}
