package com.kh.spring.memo.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface MemoService {

	ArrayList<HashMap<String, String>> selectMemoList();

	int insertMemo(Map<String, String> list);
	
	
	
}
