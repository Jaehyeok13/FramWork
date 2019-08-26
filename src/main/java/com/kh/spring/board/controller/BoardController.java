package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.exception.BoardException;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.PageInfo;
import com.kh.spring.common.Pagination;

// Servlet 에 bean 등록 하는 것을 어노테이션으로 대체 한다 Controller 로 한다. -> service 로 간다. 이동선 주입 DI 
@Controller
public class BoardController {
//	service 로 간다. 이동선 주입 DI 인터페이스로 느슨한 관계를 위해서
	
	@Autowired
	BoardService bService;
	
	// 게시판 이동 페이징
	@RequestMapping("blist.do")
	public ModelAndView boardList(@RequestParam(value="page", required=false) Integer page, ModelAndView mv) throws BoardException {
		
		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		int listCount = bService.getListCount();
		
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount);
		
		ArrayList<Board> list = bService.selectList(pi); 
		
		if(list != null) {
			mv.addObject("list", list);
			mv.addObject("pi",pi);
			mv.setViewName("board/boardListView");
		}else {
			throw new BoardException("게시글 전체 조회에 실패하였습니다.");
		}
		
		return mv;
	}
	// 글쓰기 폼 뷰 이동
	@RequestMapping("binsertView.do")
	public String boardInsertView() {
		return "board/boardInsertForm";
	}
	
	// 파일 있기 때문에 Multipart
	@RequestMapping("binsert.do")
	public String boardInsert(@ModelAttribute Board b, @RequestParam(value="uploadFile" , required=false) MultipartFile uploadFile,
								HttpServletRequest request) throws BoardException {
		
		System.out.println(b);
		System.out.println(uploadFile);
		System.out.println(uploadFile.getOriginalFilename());
		// 파일을 넣지 않은 경우 파일 이름은 "" 로 들어감
		
	//	if(!uploadFile.getOriginalFilename().equals("")) { 다른 걸로 하며는
		if(uploadFile != null && !uploadFile.isEmpty()) {
			// 저장할 경로를 지정하는 saveFile() 메소드 생성
			String renameFileName = saveFile(uploadFile, request);
			if(renameFileName != null) {
				b.setOriginalFile(uploadFile.getOriginalFilename());
				b.setRenameFile(renameFileName);
			}
			
		}
		
		int result = bService.insertBoard(b);
		
		if(result > 0) {
			return "redirect:blist.do"; // 글쓴 다음 리스트 로 이동
		}else {
			throw new BoardException("게시글 등록에 실패하였습니다.");
		}
		
		
	}
	
	public String saveFile(MultipartFile file,HttpServletRequest request) {
		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "\\buploadFiles";
		
		File folder = new File(savePath);
		// 폴더 파일이 없으면 만들어라
		if(!folder.exists()) {
			folder.mkdir();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String originalFileName = file.getOriginalFilename();
		String renameFileName = sdf.format(new java.sql.Date(System.currentTimeMillis())) + "." 
									+ originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
		
		String renamePath = folder + "\\" + renameFileName;
		
		try {
			file.transferTo(new File(renamePath));
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return renameFileName;
	}
	
	
	// 게시글 상세 보기
	@RequestMapping("bdetail.do")
	public ModelAndView boardDetail(@RequestParam("bId") int bId, @RequestParam("page") int page, ModelAndView mv) throws BoardException {
		// 조회수 증가 시키긴다.
		bService.addReadCount(bId);
	
		// 상세 페이지 이동
		Board board = bService.selectBoard(bId);
		
		if(board != null) {
			mv.addObject("board",board).addObject("page",page).setViewName("board/boardDetailView");
		
		}else {
			throw new BoardException("게시글 상세보기에 실패하였습니다.");
		}
		return mv;
		
	}
	
	// 
	@RequestMapping("bupView.do")
	public ModelAndView boardUpdateView(@RequestParam("bId") int bId, @RequestParam("page") int page, ModelAndView mv) {
		Board board = bService.selectBoard(bId);
		
		mv.addObject("board",board).addObject("page",page).setViewName("board/boardUpdateForm");
		
		return mv;
	}
	
	// 수정하고 나서 디테일 페이지 돌아오고 목록보기 누르면 페이지 값이 필요 하다
	@RequestMapping("bupdate.do")
	public ModelAndView boardUpdate(ModelAndView mv, @ModelAttribute Board b, @RequestParam("page") Integer page, 
									@RequestParam("reloadFile") MultipartFile reloadFile, HttpServletRequest request) throws BoardException {
		
		if(reloadFile != null && !reloadFile.isEmpty()) {
			// 등록할 파일이 있는지 구분 (여기로 오면 있다.)
			if(b.getRenameFile() != null) {
				// 기존에 올린 파일이 있으면 제거 해라 (deleteFile 메소드 만든다.
				deleteFile(b.getRenameFile(),request);
			}
			
			String renameFileName = saveFile(reloadFile, request);
			
			if(renameFileName != null) {
				b.setOriginalFile(reloadFile.getOriginalFilename());
				b.setRenameFile(renameFileName);
			}
		}
		
		int result = bService.updateBoard(b);
		
		if(result > 0) {
			mv.addObject("page",page).setViewName("redirect:bdetail.do?bId=" + b.getbId());
			
		}else {
			throw new BoardException("게시글 수정에 실패 하였습니다.");
		}
		
		return mv;
	}
	
	// 기존에 있던 파일 삭제
	public void deleteFile(String fileName, HttpServletRequest request) {
		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "\\buploadFiles";
	
		File f = new File(savePath + "\\" + fileName);
		
		// 파일이 존재 하면 지워라
		if(f.exists()) {
			f.delete();
		}
	
	}
	
	// 게시판 삭제 하기 삭제후 페이지 리스트 로 돌아가기
	@RequestMapping("bdelete.do")
	public ModelAndView deleteBoard(@ModelAttribute Board b, @RequestParam("page") Integer page, @RequestParam("bId") int bId , ModelAndView mv) throws BoardException {
		
		int result = bService.deleteBoard(bId);
		
		if(result > 0) {
			mv.addObject("page",page).setViewName("redirect:blist.do?page" + page);
		}else {
			throw new BoardException("게시글 삭제에 실패 하였습니다.");
		}
		
		return mv;
	}
	
	
}
