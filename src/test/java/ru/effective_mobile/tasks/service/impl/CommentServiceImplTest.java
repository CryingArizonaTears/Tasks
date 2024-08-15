package ru.effective_mobile.tasks.service.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import ru.effective_mobile.tasks.config.ExtendedModelMapper;
import ru.effective_mobile.tasks.dto.CommentDto;
import ru.effective_mobile.tasks.dto.UserDto;
import ru.effective_mobile.tasks.model.Comment;
import ru.effective_mobile.tasks.repository.CommentRepository;
import ru.effective_mobile.tasks.service.UserAuthenticationService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class CommentServiceImplTest {

    @InjectMocks
    CommentServiceImpl commentService;

    @Mock
    CommentRepository commentRepository;

    @Mock
    ModelMapper modelMapper;

    @Mock
    ExtendedModelMapper extendedModelMapper;

    @Mock
    UserAuthenticationService userAuthenticationService;

    Comment comment;
    CommentDto commentDto;
    UserDto currentUser;

    @BeforeEach
    void setUp() {
        currentUser = new UserDto();
        currentUser.setId(1L);

        comment = new Comment();
        comment.setId(1L);
        comment.setText("testComment");

        commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setText("testComment");
        commentDto.setUser(new UserDto());
        commentDto.getUser().setId(1L);
    }

    @Test
    void testGetById_Successful() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(modelMapper.map(comment, CommentDto.class)).thenReturn(commentDto);

        CommentDto foundComment = commentService.getById(1L);

        assertNotNull(foundComment, "CommentDto should not be null");
        assertEquals(1L, foundComment.getId());
        assertEquals("testComment", foundComment.getText());
    }

    @Test
    void testGetAllByTaskId_Successful() {
        List<Comment> comments = List.of(comment);
        List<CommentDto> commentDtos = List.of(commentDto);

        when(commentRepository.findAllByTaskId(1L)).thenReturn(Optional.of(comments));
        when(extendedModelMapper.mapList(comments, CommentDto.class)).thenReturn(commentDtos);

        List<CommentDto> foundComments = commentService.getAllByTaskId(1L);

        assertEquals(1, foundComments.size());
        assertEquals(1L, foundComments.get(0).getId());
        assertEquals("testComment", foundComments.get(0).getText());
    }

    @Test
    void testAddToTask_Successful() {
        when(userAuthenticationService.getCurrent()).thenReturn(currentUser);
        when(modelMapper.map(commentDto, Comment.class)).thenReturn(comment);
        when(commentRepository.save(comment)).thenReturn(comment);
        when(modelMapper.map(comment, CommentDto.class)).thenReturn(commentDto);

        CommentDto createdComment = commentService.addToTask(commentDto);

        assertNull(createdComment.getId());
        assertEquals("testComment", createdComment.getText());
        assertEquals(1L, createdComment.getUser().getId());
    }

    @Test
    void testEdit_Successful() {
        commentDto.setText("editedComment");

        when(userAuthenticationService.getCurrent()).thenReturn(currentUser);
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(modelMapper.map(any(Comment.class), eq(CommentDto.class))).thenReturn(commentDto);
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentDto updatedComment = commentService.edit(commentDto);

        assertEquals("editedComment", updatedComment.getText());
    }

    @Test
    void testDelete_Successful() {
        when(userAuthenticationService.getCurrent()).thenReturn(currentUser);
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        commentService.delete(1L);

        verify(commentRepository, times(1)).deleteById(1L);
    }
}