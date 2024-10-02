package com.example.exodia.board.dto;

import com.example.exodia.board.domain.Category;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardListResDto {

    private Long id;
    private String title;
    private Category category;
    private Long hits;
    private String user_num;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isPinned;
}