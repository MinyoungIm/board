package com.spring.board.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED) // 파라미터가 없는 기본 생성자를 추가하는 어노테이션, access는 생성자의 접근 권한을 설정하는 속성
@Getter // 모든 필드에 getter를 자동생성 해주는 어노테이션
@Entity // 객체를 테이블과 매핑 할 엔티티라고 JPA에게 알려주는 역할
@Table(name = "board")
public class BoardEntity extends TimeEntity{

	@Id // 테이블의 기본 키임을 명시
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키로 대체키를 사용할 때, 기본키 값 생성 전략을 명시
	private Long id;
	
	@Column(length = 10, nullable = false)
	private String writer;
	
	@Column(length = 100, nullable = false)
	private String title;
	
	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;
	
	@Builder
	public BoardEntity(Long id, String title, String content, String writer) {
		this.id = id;
		this.writer = writer;
		this.title = title;
		this.content = content;
	}
	
	
}
