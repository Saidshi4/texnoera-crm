package com.example.texnoeracrm.service;

import com.example.texnoeracrm.dao.entity.CommentEntity;
import com.example.texnoeracrm.dao.entity.UserEntity;
import com.example.texnoeracrm.dao.entity.UserTaskEntity;
import com.example.texnoeracrm.dao.repository.CommentRepository;
import com.example.texnoeracrm.dao.repository.UserRepository;
import com.example.texnoeracrm.dao.repository.UserTaskRepository;
import com.example.texnoeracrm.enums.ExceptionEnum;
import com.example.texnoeracrm.enums.RoleEnum;
import com.example.texnoeracrm.exception.NotFoundException;
import com.example.texnoeracrm.mapper.CommentMapper;
import com.example.texnoeracrm.model.get.CommentGetDto;
import com.example.texnoeracrm.model.set.CommentSetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserTaskRepository userTaskRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;

    public UserEntity findUserByUserId(Long userId) {
        log.info("ActionLog.userFindById.start userId {}", userId);
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        ExceptionEnum.USER_NOT_FOUND.name(),
                        String.format(ExceptionEnum.USER_NOT_FOUND.getLog(), userId)
                ));
        log.info("ActionLog.userFindById.end userId {}", userId);
        return userEntity;
    }

    public UserTaskEntity findUserTask(Long id) {
        log.info("ActionLog.userTask.start id {}", id);
        UserTaskEntity userTask = userTaskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        ExceptionEnum.USER_TASK_NOT_FOUND.name(),
                        String.format(ExceptionEnum.USER_TASK_NOT_FOUND.getLog(), id)
                ));
        log.info("ActionLog.userTask.end id {}", id);
        return userTask;
    }

    public List<CommentGetDto> writeComment(Long userId, CommentSetDto commentSetDto){
        log.info("ActionLog.writeComment.start");
        UserTaskEntity userTaskEntity = findUserTask(commentSetDto.getUserTaskId());
        UserEntity userEntity = findUserByUserId(userId);
        CommentEntity commentEntity = CommentEntity.builder()
                .text(commentSetDto.getText())
                .taskEntity(userTaskEntity)
                .author(userEntity.getRoleEntity().getName())
                .createdAt(LocalDateTime.now())
                .build();
        commentRepository.save(commentEntity);
        userTaskEntity = findUserTask(commentSetDto.getUserTaskId());
        List<CommentGetDto> comments = commentMapper.mapToDtos(userTaskEntity.getCommentEntities());
        log.info("ActionLog.writeComment.end");
        return comments;
    }
}
