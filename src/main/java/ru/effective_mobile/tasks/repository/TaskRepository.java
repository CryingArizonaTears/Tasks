package ru.effective_mobile.tasks.repository;

import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.effective_mobile.tasks.model.Task;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    @Query(value = "Select * From task Where author_id = ?1", nativeQuery = true)
    Optional<List<Task>> findAllByAuthorId(Long userId);
}
