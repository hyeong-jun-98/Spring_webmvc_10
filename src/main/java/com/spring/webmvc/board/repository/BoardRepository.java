package com.spring.webmvc.board.repository;


import com.spring.webmvc.board.domain.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// 역할 : 게시물 데이터를 저장 조회 수정 삭제하는 책임을 부여바듬
// SQL Mapper 인터페이스
// Mybatis 설정파일 없이 접속하는법
@Mapper
public interface BoardRepository {

    // 게시글 목록 조회
    List<Board> findAll();


    // 게시글 상세 단일 조회
     Board findOne(Long boardNo);

     // 게시글 작성
     boolean save(Board board);

     //게시글 삭제
    boolean remove(Long boardno);

    // 게시글 수정
    boolean modify(Board board);

}
