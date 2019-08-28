package com.kh.spring.board.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.BoardDAO;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.PageInfo;
import com.kh.spring.board.model.vo.Reply;

// 먼저 서비스를 연결하자 @Autowired 로 되어있는 선언된 변수 연결 한다.
@Service("bService")
public class BoardServiceImpl implements BoardService{
	// dao 도 연결 되어야 하기 때문에
	
	@Autowired
	BoardDAO bDAO; 
	
	@Override
	public int getListCount() {
		
		return bDAO.getListCount();
	}

	@Override
	public ArrayList<Board> selectList(PageInfo pi) {
		
		return bDAO.selectList(pi);
	}

	@Override
	public int insertBoard(Board b) {
		return bDAO.insertBoard(b);
	}

	@Override
	public void addReadCount(int bId) {
		bDAO.addReadCount(bId);
	}

	@Override
	public Board selectBoard(int bId) {
		return bDAO.selectBoard(bId);
	}

	@Override
	public int updateBoard(Board b) {
		return bDAO.updateBoad(b);
	}

	@Override
	public int deleteBoard(int bId) {
		return bDAO.deleteBoard(bId);
	}

	@Override
	public ArrayList<Board> selectTopList() {
		return bDAO.selectTopList();
	}

	@Override
	public ArrayList<Reply> selectReplyList(int bId) {
		return bDAO.selectRelyList(bId);
	}

	// 댓글 등록
	@Override
	public int insertReply(Reply r) {
		return bDAO.insertReply(r);
	}

	

	
	
}
