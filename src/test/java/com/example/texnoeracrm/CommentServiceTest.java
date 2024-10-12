package com.example.texnoeracrm;

import com.example.texnoeracrm.dao.entity.CommentEntity;
import com.example.texnoeracrm.dao.entity.UserEntity;
import com.example.texnoeracrm.dao.entity.UserTaskEntity;
import com.example.texnoeracrm.dao.repository.CommentRepository;
import com.example.texnoeracrm.dao.repository.UserRepository;
import com.example.texnoeracrm.dao.repository.UserTaskRepository;
import com.example.texnoeracrm.enums.ExceptionEnum;
import com.example.texnoeracrm.exception.NotFoundException;
import com.example.texnoeracrm.mapper.CommentMapper;
import com.example.texnoeracrm.model.get.CommentGetDto;
import com.example.texnoeracrm.model.set.CommentSetDto;
import com.example.texnoeracrm.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserTaskRepository userTaskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindUserByUserId_UserFound() {
        Long userId = 1L;
        UserEntity userEntity = new UserEntity();

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        UserEntity result = commentService.findUserByUserId(userId);

        assertEquals(userEntity, result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testFindUserByUserId_UserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        NotFoundException exception =
                assertThrows(NotFoundException.class, () -> commentService.findUserByUserId(userId));

        assertEquals(ExceptionEnum.USER_NOT_FOUND.name(), exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testFindUserTask_TaskFound() {
        Long taskId = 1L;
        UserTaskEntity taskEntity = new UserTaskEntity();

        when(userTaskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));

        UserTaskEntity result = commentService.findUserTask(taskId);

        assertEquals(taskEntity, result);
        verify(userTaskRepository, times(1)).findById(taskId);
    }

    @Test
    void testFindUserTask_TaskNotFound() {
        Long taskId = 1L;

        when(userTaskRepository.findById(taskId)).thenReturn(Optional.empty());

        NotFoundException exception =
                assertThrows(NotFoundException.class, () -> commentService.findUserTask(taskId));

        assertEquals(ExceptionEnum.USER_TASK_NOT_FOUND.name(), exception.getMessage());
        verify(userTaskRepository, times(1)).findById(taskId);
    }

//    @Test
//    void testWriteComment() {
//        Long userId = 1L;
//        CommentSetDto commentSetDto = new CommentSetDto();
//        commentSetDto.setUserTaskId(1L);
//        commentSetDto.setText("Test comment");
//
//        UserEntity userEntity = new UserEntity();
//        UserTaskEntity userTaskEntity = new UserTaskEntity();
//        CommentEntity commentEntity = new CommentEntity();
//        List<CommentEntity> commentEntities = List.of(commentEntity);
//        List<CommentGetDto> commentDtos = List.of(new CommentGetDto());
//
//        when(userTaskRepository.findById(commentSetDto.getUserTaskId())).thenReturn(Optional.of(userTaskEntity));
//        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
//        when(commentMapper.mapToDtos(commentEntities)).thenReturn(commentDtos);
//
//        List<CommentGetDto> result = commentService.writeComment(userId, commentSetDto);
//
//        assertEquals(commentDtos, result);
//        verify(commentRepository, times(1)).save(any(CommentEntity.class));
//        verify(userTaskRepository, times(2)).findById(commentSetDto.getUserTaskId());
//        verify(userRepository, times(1)).findById(userId);
//    }

}
