package ru.practicum.shareit.comment.dto;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.Valid;
import java.time.LocalDateTime;

public interface CommentTest {

//    void setId(Long id);
//
//    void setText(String text);
//
//    void setCreated(LocalDateTime created);
//
//    void setAuthorName(String authorName);

    Long getId();

    String getText();

    LocalDateTime getCreated();

    @Value("#{target.author.name}")
    String getAuthorName();
}
