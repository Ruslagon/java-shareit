package ru.practicum.shareit.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.dto.CommentTest;
import ru.practicum.shareit.comment.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    CommentTest findByIdAndAuthorId(Long id, Long authorId);
    CommentTest findByIdAndAuthorIdAndItemId(Long id, Long authorId, Long itemId);
    List<CommentTest> findAllByItemId(Long itemId);

}