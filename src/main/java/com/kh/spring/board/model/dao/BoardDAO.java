package com.kh.spring.board.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.PageInfo;

@Repository("bDAO")
public class BoardDAO {

	@Autowired
	SqlSessionTemplate sqlSession;

	public int getListCount() {
		return sqlSession.selectOne("boardMapper.getListCount");
	}

	//
	public ArrayList<Board> selectList(PageInfo pi) {
	
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit(); // 내가 1페이지에서 뛰어 넘은거는 0개 계산법 잘해야 한다.
		
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit()); // rowBounds 만큼 뛰어 넘을건데 기준은 offset
		

		return (ArrayList)sqlSession.selectList("boardMapper.selectList",null, rowBounds); // 빨간 줄 형 변환 해준다.
	}

	public int insertBoard(Board b) {
		return sqlSession.insert("insertBoard",b);
	}

	public void addReadCount(int bId) {
		sqlSession.update("boardMapper.updateCount", bId);
	}

	public Board selectBoard(int bId) {
		return sqlSession.selectOne("boardMapper.selectBoard", bId);
	}

	public int updateBoad(Board b) {
		return sqlSession.update("boardMapper.updateBoard",b);
	}

	// 게시글 삭제 하기
	public int deleteBoard(int bId) {
		return sqlSession.update("boardMapper.deleteBoard", bId);
	}
	
	
}
