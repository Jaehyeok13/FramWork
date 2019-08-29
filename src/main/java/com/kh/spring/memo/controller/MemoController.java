package com.kh.spring.memo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.memo.model.service.MemoService;

@Controller
public class MemoController {
	
	@Autowired
	MemoService mmService; // 의존성 Autowired
	
//	private Logger logger = LoggerFactory.getLogger(MemoController.class);
	
	@RequestMapping("memo.do")
	public ModelAndView memo() {
		
//		logger.debug("[Befor]MemoController - memo()");
		
		ArrayList<HashMap<String,String>> list = mmService.selectMemoList();
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("list",list);
		mv.setViewName("memo/memo");
		
//		logger.debug("[After]MEmoController - mome()");
		
		return mv;
	}
	
	@RequestMapping("memo/insertMemo.do")
	public ModelAndView insertMemo(@RequestParam("memo") String memo, @RequestParam("password") String password, ModelAndView mv) {
		
		
		Map<String,String> list = new HashMap<String,String>();
		
		list.put("memo",memo );
		list.put("password",password);
		
		int result = mmService.insertMemo(list);
			
		if(result > 0) {
			mv.setViewName("redirect:/memo.do");
		}
		
		
		return mv;
		
	}
	
	@RequestMapping("*/delete.do")
	public String delete() {
		
		System.out.println("mapping 으로 넘어옴");
		
		return "redirect:/memo.do";
	
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
