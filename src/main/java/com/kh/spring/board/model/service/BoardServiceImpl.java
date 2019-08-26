package com.kh.spring.board.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.BoardDAO;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.PageInfo;

// 먼저 서비스를 연결하자
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
	
}
