package com.spring.board.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.board.domain.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Long>{
// JpaRepository -> 데이터 조작을 다루는 함수가 정의되어 있기 때문에, CRUD 작업이 편해짐
	List<BoardEntity> findByTitleContaining(String keyword); //Containing을 붙여주면 Like 검색이 된다 -> %{keyword}% 이렇게 표현이됨
}
