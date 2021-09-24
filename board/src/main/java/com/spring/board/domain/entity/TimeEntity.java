package com.spring.board.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass // 테이블로 매핑하지 않고, 자식 클래스(엔티티)에게 매핑정보를 상속
@EntityListeners(AuditingEntityListener.class) // JPA에게 해당 Entity는 Auditing기능을 사용한다는 것을 알림
public class TimeEntity {
	
	@CreatedDate // 속성을 추가하지 않으면 수정 시, 해당 값은 null, 이때 생성일은 update할 필요가 없으므로, updatable = false 속성을 추가
	@Column(updatable = false)
	private LocalDateTime createdDate;
	
	@LastModifiedDate
	private LocalDateTime modifiedDate;

}
