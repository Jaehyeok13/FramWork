package com.kh.spring.memo.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.memo.model.dao.MemoDAO;

@Service("mmService")
public class MemoServiceImpl implements MemoService{
	
	@Autowired
	MemoDAO mmDAO; 
	
	private Logger logger = LoggerFactory.getLogger(MemoServiceImpl.class);

	@Override
	public ArrayList<HashMap<String, String>> selectMemoList() {
//		logger.debug("[Before]MemoServiceImpl - selectMemoList()");
//		
//		ArrayList<HashMap<String,String>> list = mmDAO.selectMemoList();
//		
//		logger.debug("[After]MemoServiceImpl- selectMemoList()");
		return mmDAO.selectMemoList() ;
//		return list;
	}

	@Override
	public int insertMemo(Map<String, String> list) {
//		logger.debug("[Before]MemoServiceImpl - insertMemo()");
//		
//		logger.debug("[After]MemoServiceImpl - insertMemo()");
		return mmDAO.insertMemo(list);

	}

}
