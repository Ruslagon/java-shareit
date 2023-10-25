package ru.practicum.shareit.item.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Where;
import ru.practicum.shareit.comment.dto.CommentTest;

import javax.persistence.FetchType;
import java.util.List;

public interface ItemInfo {

    Long getId();

    String getName();

    String getDescription();

    Boolean getAvailable();

    Long getRequestId();

    List<CommentTest> getComments();
}
