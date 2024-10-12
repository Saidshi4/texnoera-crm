package com.example.texnoeracrm.controller;

import com.example.texnoeracrm.model.get.CommentGetDto;
import com.example.texnoeracrm.model.set.CommentSetDto;
import com.example.texnoeracrm.service.CommentService;
import com.example.texnoeracrm.service.auth.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final JwtService jwtService;

    @PostMapping
    public List<CommentGetDto> writeComment(HttpServletRequest request, @RequestBody CommentSetDto commentSetDto) {
        String token = (String) request.getAttribute("token");
        Long userId = jwtService.extractUserIdFromAccessToken(token, true);
        return commentService.writeComment(userId, commentSetDto);
    }
}
