package com.spring.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.board.domain.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Long>{
// JpaRepository -> 데이터 조작을 다루는 함수가 정의되어 있기 때문에, CRUD 작업이 편해짐
}
