package ru.effective_mobile.tasks.service;

import javax.xml.stream.events.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<List<Comment>> getAllByTaskId(Long id);

    Optional<Comment> addToTask(Long id, Comment comment);

    Optional<Comment> edit(Comment comment);

    void delete(Long id);

}
