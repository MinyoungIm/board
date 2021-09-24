package com.spring.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.board.dto.BoardDto;
import com.spring.board.service.BoardService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor // Bean 주입 방식과 관련이 있으며, 생성자로 Bean 객체를 받는 방식을 해결해주는 어노테이션
public class BoardController {

	private BoardService boardService;
	
	@GetMapping("/")
	public String list(Model model) {
		List<BoardDto> boardList = boardService.getBoardlist();
		
		model.addAttribute("boardList", boardList);
		return "board/list.html";
	}
	
	@GetMapping("/post")
	public String write() {
		return "board/write.html";
	}
	
	@PostMapping("/post")
	public String write(BoardDto boardDto) {
		boardService.savePost(boardDto);
		
		return "redirect:/";
	}
	
	
	
	
}
