package com.spring.board.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.spring.board.domain.entity.BoardEntity;
import com.spring.board.dto.BoardDto;
import com.spring.board.repository.BoardRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor // Repository를 주입
@Service // 서비스 계층임을 명시
public class BoardService {

	private BoardRepository boardRepository;
	
	@Transactional // 트랜잭션을 적용
	public Long savePost(BoardDto boardDto) {
		return boardRepository.save(boardDto.toEntity()).getId(); // save() -> JpaRepository에 정의된 메서드로, DB에 INSERT, UPDATE를 담당
	}
	
	@Transactional
	 public List<BoardDto> getBoardlist() {
        List<BoardEntity> boardEntities = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for ( BoardEntity boardEntity : boardEntities) {
            BoardDto boardDTO = BoardDto.builder()
                    .id(boardEntity.getId())
                    .title(boardEntity.getTitle())
                    .content(boardEntity.getContent())
                    .writer(boardEntity.getWriter())
                    .createdDate(boardEntity.getCreatedDate())
                    .build();

            boardDtoList.add(boardDTO);
        }

        return boardDtoList;
    }
	
}
