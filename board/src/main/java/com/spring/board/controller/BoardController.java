package com.spring.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.board.dto.BoardDto;
import com.spring.board.service.BoardService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor // Bean 주입 방식과 관련이 있으며, 생성자로 Bean 객체를 받는 방식을 해결해주는 어노테이션
public class BoardController {

	private BoardService boardService;
	
	/* 리스트 */
	
	@GetMapping("/")
	public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
	List<BoardDto> boardList = boardService.getBoardlist(pageNum);
	Integer[] pageList = boardService.getPageList(pageNum);

	model.addAttribute("boardList", boardList);
	model.addAttribute("pageList", pageList);

	return "board/list.html";
	}
	
	/* 글쓰기 */
	
	@GetMapping("/post")
	public String write() {
		return "board/write.html";
	}
	
	@PostMapping("/post")
	public String write(BoardDto boardDto) {
		boardService.savePost(boardDto);
		
		return "redirect:/";
	}
	
	
	/* 상세조회 */
	
	@GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model) {
        BoardDto boardDTO = boardService.getPost(no);

        model.addAttribute("boardDto", boardDTO);
        return "board/detail.html";
    }
	
	
	/* 글 수정 */
	@GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        BoardDto boardDTO = boardService.getPost(no);

        model.addAttribute("boardDto", boardDTO);
        return "board/update.html";
    }

    @PutMapping("/post/edit/{no}")
    public String update(BoardDto boardDTO) {
        boardService.savePost(boardDTO);

        return "redirect:/";
    }
	
	
	/* 삭제 */
	@DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long no) {
        boardService.deletePost(no);

        return "redirect:/";
    }
	
	/* 검색 */
	
	@GetMapping("/board/search")
	public String search(@RequestParam(value="keyword") String keyword, Model model) {
		List<BoardDto> boardDtoList = boardService.searchPosts(keyword);
		model.addAttribute("boardList",boardDtoList);
		return "board/list.html";
	}
	


	
}
