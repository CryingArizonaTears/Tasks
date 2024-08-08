package ru.effective_mobile.tasks.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.effective_mobile.tasks.config.ExtendedModelMapper;
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
    ExtendedModelMapper extendedModelMapper;

    @Override
    public Optional<CommentDto> getById(Long id) {
        return Optional.ofNullable(modelMapper.map(commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found")), CommentDto.class));
    }

    @Override
    public Optional<List<CommentDto>> getAllByTaskId(Long id) {
        Optional<List<Comment>> commentsFromRepo = Optional.ofNullable(commentRepository.findAllByTaskId(id).orElseThrow(() -> new EntityNotFoundException("Comments not found")));
        return Optional.ofNullable(extendedModelMapper.mapList(commentsFromRepo.get(), CommentDto.class));
    }

    @Override
    public Optional<CommentDto> addToTask(CommentDto commentDto) {
        return Optional.ofNullable(modelMapper.map(commentRepository.save(modelMapper.map(commentDto, Comment.class)), CommentDto.class));
    }

    @Override
    public Optional<CommentDto> edit(CommentDto commentDto) {
        Optional<CommentDto> commentForEdit = getById(commentDto.getId());
        commentForEdit.get().setText(commentDto.getText());
        return Optional.ofNullable(modelMapper.map(commentRepository.save(modelMapper.map(commentDto, Comment.class)), CommentDto.class));
    }

    @Override
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
