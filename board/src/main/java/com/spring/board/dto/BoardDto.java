package com.spring.board.dto;
// 데이터 전달 객체 dto
import java.time.LocalDateTime;

import com.spring.board.domain.entity.BoardEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto { // dto는 Controller <-> Service <-> Repository

	private Long id;
	private String writer;
	private String title;
	private String content;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	
	public BoardEntity toEntity() { //dto에서 필요한 부분을 빌더패턴을 통해 entity로
		BoardEntity boardEntity = BoardEntity.builder()
				.id(id)
				.writer(writer)
				.title(title)
				.content(content)
				.build();
		return boardEntity;
				
	}
	
	@Builder
    public BoardDto(Long id, String title, String content, String writer, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
	
}
