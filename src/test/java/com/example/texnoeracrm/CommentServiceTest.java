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


}
