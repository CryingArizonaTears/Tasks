package ru.effective_mobile.tasks.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.effective_mobile.tasks.dto.CommentDto;
import ru.effective_mobile.tasks.model.Comment;
import ru.effective_mobile.tasks.repository.CommentRepository;
import ru.effective_mobile.tasks.service.CommentService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentServiceImpl implements CommentService {

    CommentRepository commentRepository;
    ModelMapper modelMapper;

    @SneakyThrows
    @Override
    public Optional<CommentDto> getById(Long id) {
        return Optional.ofNullable(modelMapper.map(commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found")), CommentDto.class));
    }

    @Override
    public Optional<List<CommentDto>> getAllByTaskId(Long id) {
        Optional<List<Comment>> comments = Optional.ofNullable(commentRepository.findAllByTaskId(id).orElseThrow(() -> new EntityNotFoundException("Comments not found")));
        return ;
    }

    @Override
    public Optional<CommentDto> addToTask(Long id, CommentDto commentDto) {
        return Optional.empty();
    }

    @Override
    public Optional<CommentDto> edit(CommentDto commentDto) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
