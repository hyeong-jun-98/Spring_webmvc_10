package com.spring.webmvc.board.service;

import com.spring.webmvc.board.domain.Board;
import com.spring.webmvc.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

// 역할 : 컨트롤러와 레파지토리 사이의 중산 잡다한 처리를 담당
@RequiredArgsConstructor // final 필드 초기화 생성자를 만듦
@Service
public class BoardService {

    private final BoardRepository repository;

    // 전체조회 중간처리
    public List<Board> getList() {
        List <Board> boardList = repository.findAll();

        // 게시물 제목 줄임처리
        // 6글자 이상이면 ... 처리
        processBoardList(boardList);

        return boardList;
    }

    private static void processBoardList(List<Board> boardList) {
        for (Board board : boardList) {
            subStringitle(board);
            convertDateFormat(board);
            isNewArticle(board);

        }
    }

    private static void isNewArticle(Board board) {
        // 신규 게시물 new 마크 처리 (10분 이내)
        long regdate = board.getRegDate().getTime();    // 게시물 작성 시간
        long nowDate = System.currentTimeMillis();      // 현재 시간 밀리초

        long diff = nowDate - regdate;          // 차이
        long limit = 10 * 60 * 1000;        // 10분을 밀리초로 변환
        // 3 * 60 * 60 * 1000 3시간
        if(diff <= limit) {
            board.setNewArticle(true);
        }
    }

    private static void convertDateFormat(Board board) {
        // 날짜 포맷팅 처리
        Date regDate = board.getRegDate();
        SimpleDateFormat sdf = new SimpleDateFormat("y-MM-dd a hh:mm");
        board.setPrettierDate(sdf.format(regDate));
    }

    private static void subStringitle(Board board) {
        String title = board.getTitle();
        if(title.length() > 6) {
            String shortTitle = title.substring(0,6) + "...";
            board.setShortTitle(shortTitle);
        } else {
            board.setShortTitle(title);
        }
    }

    // 상세조회 중간처리
    public Board getDetail(Long boardNo) {
        Board board = repository.findOne(boardNo);
        return board;
    }

    // 게시물 저장 중간처리
    public boolean insert(Board board) {
        boolean flag = repository.save(board);
        return flag;
    }

    // 게시물 수정 중간처리
    public boolean update(Board board) {
        boolean flag = repository.modify(board);


        return flag;
    }

    // 게시물 삭제 중간처리
    public boolean delete(Long boardNo) {
        boolean flag = repository.remove(boardNo);
        return flag;
    }



}
