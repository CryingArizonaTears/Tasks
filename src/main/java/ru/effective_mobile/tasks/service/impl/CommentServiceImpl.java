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
import ru.effective_mobile.tasks.service.UserAuthenticationService;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentServiceImpl implements CommentService {

    CommentRepository commentRepository;
    ModelMapper modelMapper;
    ExtendedModelMapper extendedModelMapper;
    UserAuthenticationService userAuthenticationService;


    @Override
    public CommentDto getById(Long id) {
        return modelMapper.map(commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found")), CommentDto.class);
    }

    @Override
    public List<CommentDto> getAllByTaskId(Long id) {
        List<Comment> commentsFromRepo = commentRepository.findAllByTaskId(id).orElseThrow(() -> new EntityNotFoundException("Comments not found"));
        return extendedModelMapper.mapList(commentsFromRepo, CommentDto.class);
    }

    @Override
    public CommentDto addToTask(CommentDto commentDto) {
        commentDto.setId(null);
        commentDto.setUser(userAuthenticationService.getCurrent());
        return modelMapper.map(commentRepository.save(modelMapper.map(commentDto, Comment.class)), CommentDto.class);
    }

    @Override
    public CommentDto edit(CommentDto commentDto) {
        CommentDto commentFromRepo = getById(commentDto.getId());
        if (!commentFromRepo.getUser().getId().equals(userAuthenticationService.getCurrent().getId())) {
            throw new SecurityException("You have no access to comment with " + commentDto.getId() + " id");
        } else {
            commentFromRepo.setText(commentDto.getText());
            return modelMapper.map(commentRepository.save(modelMapper.map(commentFromRepo, Comment.class)), CommentDto.class);
        }
    }

    @Override
    public void delete(Long id) {
        CommentDto commentFromRepo = getById(id);
        if (!commentFromRepo.getUser().getId().equals(userAuthenticationService.getCurrent().getId())) {
            throw new SecurityException("You have no access to comment with " + id + " id");
        } else {
            commentRepository.deleteById(id);
        }
    }
}
