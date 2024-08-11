package ru.effective_mobile.tasks.service;

import ru.effective_mobile.tasks.dto.CommentDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> getAllByTaskId(Long id);

    CommentDto getById(Long id);

    CommentDto addToTask(CommentDto commentDto);

    CommentDto edit(CommentDto commentDto);

    void delete(Long id);

}
