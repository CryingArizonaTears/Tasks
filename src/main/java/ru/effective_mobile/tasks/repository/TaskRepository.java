package ru.effective_mobile.tasks.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.effective_mobile.tasks.model.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
}
