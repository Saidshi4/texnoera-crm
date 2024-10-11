package com.example.texnoeracrm.model.get;

import com.example.texnoeracrm.enums.RoleEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentGetDto {
    private Long id;
    private String text;
    @Enumerated(EnumType.STRING)
    private RoleEnum author;
    private LocalDateTime createdAt;
}
