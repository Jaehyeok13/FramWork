package com.kh.spring.board.model.service;

import java.util.ArrayList;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.PageInfo;

public interface BoardService {
	// 페이징 처리 한다고 할때 리스트의 천제 갯수를 가지고 온다
	
	int getListCount(); // 전체 갯수 가지고옴
	
	// 정수 값 바탕으로 페이징 처리 해준다.
	
	ArrayList<Board> selectList(PageInfo pi);
	
	// 이후 게시글 등록 할 수 있게
	
	int insertBoard(Board b);
	
	// 상세 페이지
	void addReadCount(int bId); // int 와 void 해도 상관 없음 int 을 활용 할지 안할지 구분 하면 된다.
	
	Board selectBoard(int bId); // board 라는 하는 vo 객체에 담는다.
	
	// 수정
	int updateBoard(Board b);
	
	// 삭제
	int deleteBoard(int bId);

	// 리스트 가져 오니간 one 이 아니고 여러개 list임
	ArrayList<Board> selectTopList();
	
	
}































