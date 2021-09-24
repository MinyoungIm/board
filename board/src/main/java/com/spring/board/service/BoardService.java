package com.spring.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	
	@Transactional
	public BoardDto getPost(Long id) {
		Optional<BoardEntity> boardEntityWrapper = boardRepository.findById(id);
		BoardEntity boardEntity = boardEntityWrapper.get();
		
		/*
		 * findById() PK 값을 where 조건으로 하여, 데이터를 가져오기 위한 메서드이며, JpaRepository 인터페이스에서
		 * 정의되어 있음 반환 값은 Optional 타입인데, 엔티티를 빼오려면 boardEntityWrapper.get(); 이렇게 get() 메서드를 사용해서 가져옴
		 */
		
		BoardDto boardDTO = BoardDto.builder()
				.id(boardEntity.getId())
				.title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .createdDate(boardEntity.getCreatedDate())
                .build();
		return boardDTO;
	}
	
	@Transactional
	public void deletePost(Long id) {
		boardRepository.deleteById(id);
	}
}
