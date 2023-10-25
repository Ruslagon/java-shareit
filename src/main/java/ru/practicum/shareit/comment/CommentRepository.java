package ru.practicum.shareit.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.comment.dto.CommentTest;
import ru.practicum.shareit.comment.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    CommentTest findByIdAndAuthorId(Long id, Long authorId);

}