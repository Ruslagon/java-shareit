package ru.practicum.shareit.item.model;

import ru.practicum.shareit.comment.dto.CommentTest;

import java.util.List;

public interface ItemInfo {

    Long getId();

    String getName();

    String getDescription();

    Boolean getAvailable();

    Long getRequestId();

    List<CommentTest> getComments();
}
