package com.example.texnoeracrm.model.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationGetDto {
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
