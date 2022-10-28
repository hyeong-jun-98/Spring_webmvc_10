package com.spring.webmvc.board.controller;


import com.spring.webmvc.board.domain.Board;
import com.spring.webmvc.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")  // 공통 URL 잔입점 설정
public class BoardController {

    private final BoardService service;

    // 게시물 목록 조회 요청 처리
    @GetMapping("/list")
    public String list(Model model) {

        List<Board> boardList = service.getList();
//        log.info("board/list Get 요청 발생");
        model.addAttribute("bList", boardList);
        return "board/list";
    }
    // 게시글 상세 조회 요청 처리
    @GetMapping("/content/{bno}")
    public String content(@PathVariable("bno") Long boardNo, Model model) {
        model.addAttribute("b", service.getDetail(boardNo));
        return "board/detail";
    }

    // 게시물 쓰기 화면 요청
    @GetMapping("/write")
    public String write() {

        return "board/write";
    }


    // 게시물 등록 요청
    @PostMapping("/write")
    public String write(Board board, RedirectAttributes ra) {

        boolean flag = service.insert(board);
        ra.addFlashAttribute("msg", "insert-success");
        return flag ? "redirect:/board/list" : "redirect:/";
    }



}
