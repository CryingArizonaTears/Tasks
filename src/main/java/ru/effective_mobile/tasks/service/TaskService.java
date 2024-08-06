package ru.effective_mobile.tasks.service;

import org.springframework.scheduling.config.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Optional<Task> getById (Long id);

    Optional<List<Task>> getAllByUserId (Long id);

    Optional<Task> create (Task task);

    Optional<Task> edit (Task task);

    Optional<Task> editStatus (Task task);

    void delete (Task task);

}
