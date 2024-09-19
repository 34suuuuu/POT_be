package com.example.exodia.document.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocListResDto {
	private Long id;
	private String fileName;
	private String type;
	// private User user;
	private LocalDateTime createAt;
	private Long hits;
}
