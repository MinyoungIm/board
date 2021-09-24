package com.spring.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.spring.board.domain.entity.BoardEntity;
import com.spring.board.domain.repository.BoardRepository;
import com.spring.board.dto.BoardDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor // Repository를 주입
@Service // 서비스 계층임을 명시
public class BoardService {

	private BoardRepository boardRepository;
	
	private static final int BLOCK_PAGE_NUM_COUNT = 5; // 블럭에 존재하는 페이지 번호 수
	private static final int PAGE_POST_COUNT = 4; // 한 페이지에 존재하는 게시글 수
	
	@Transactional // 트랜잭션을 적용
	public Long savePost(BoardDto boardDto) {
		return boardRepository.save(boardDto.toEntity()).getId(); // save() -> JpaRepository에 정의된 메서드로, DB에 INSERT, UPDATE를 담당
	}
	
	@Transactional
	 public List<BoardDto> getBoardlist(Integer pageNum) {
		Page<BoardEntity> page = boardRepository.findAll(PageRequest.of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "createdDate")));
		// repository의 find() 관련 메서드를 호출할 때 Pageable 인터페이스를 구현한 클래스(PageRequest.of())를 전달하면 페이징을 할 수 있습니다.
		/*
		 * 첫 번째 인자 limit을 의미 "현재 페이지 번호 - 1"을 계산한 값이며, 실제 페이지 번호와 SQL 조회시 사용되는 limit은 다르기 때문
		 * 두 번째 인자 offset을 의미 몇 개를 가져올 것인가? 
		 * 세 번째 인자 정렬 방식을 결정 createDate 컬럼을 기준으로 오름차순으로 정렬하여 가져옵니다.
		 */
		
        List<BoardEntity> boardEntities = page.getContent();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for ( BoardEntity boardEntity : boardEntities) {
        	boardDtoList.add(this.convertEntityToDto(boardEntity));
        }

        return boardDtoList;
    }
	
	@Transactional
	public Long getBoardCount() { //전체 게시글 개수
		return boardRepository.count();
	}
	
	public Integer[] getPageList(Integer curPageNum) { // 프론트에 노출시킬 페이지 번호 리스트를 계산
		Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

	// 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getBoardCount());

	// 총 게시글 기준으로 계산한 마지막 페이지 번호 계산 (올림으로 계산)
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

	// 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

	// 페이지 시작 번호 조정
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;

	// 페이지 번호 할당
        for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }

        return pageList;
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
	
    
	/* 검색 */
    @Transactional
    public List<BoardDto> searchPosts(String keyword) {
    	List<BoardEntity> boardEntities = boardRepository.findByTitleContaining(keyword);
    	List<BoardDto> boardDtoList = new ArrayList<>();
    	
    	if(boardEntities.isEmpty()) return boardDtoList;
    	
    	for(BoardEntity boardEntity : boardEntities) {
    		boardDtoList.add(this.convertEntityToDto(boardEntity));
    	}
    	
    	return boardDtoList;
    }
    
    private BoardDto convertEntityToDto(BoardEntity boardEntity) {
    	return BoardDto.builder()
    			.id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .createdDate(boardEntity.getCreatedDate())
                .build();
    }
    
}
