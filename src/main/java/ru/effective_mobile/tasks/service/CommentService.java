package ru.effective_mobile.tasks.service;

import ru.effective_mobile.tasks.dto.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<List<CommentDto>> getAllByTaskId(Long id);

    Optional<CommentDto> getById(Long id);

    Optional<CommentDto> addToTask(CommentDto commentDto);

    Optional<CommentDto> edit(CommentDto commentDto);

    void delete(Long id);

}
