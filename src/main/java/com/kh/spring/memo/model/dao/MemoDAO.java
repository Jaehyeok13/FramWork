package com.kh.spring.memo.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.memo.model.service.MemoServiceImpl;

@Repository("mmDAO")
public class MemoDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession; // root-context.xml 에서 작성한 bean 으로 생성

	private Logger logger = LoggerFactory.getLogger(MemoServiceImpl.class);
	
	public ArrayList<HashMap<String, String>> selectMemoList() {
//		logger.debug("[Before]MemoDAO - selectMemoList()");
//		ArrayList<HashMap<String, String>> list = (ArrayList)sqlSession.selectList("memoMapper.selectMemoList");
//		
//		logger.debug("[After]MemoDAO - selectMemoList()");
		return (ArrayList)sqlSession.selectList("memoMapper.selectMemoList");
//		return list;
		
		
	}

	public int insertMemo(Map<String, String> list) {
		return sqlSession.insert("memoMapper.insertMemo",list);
	}
	
}
