package ru.effective_mobile.tasks.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.effective_mobile.tasks.model.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    Optional<List<Comment>> findAllByTaskId(long taskId);
}
