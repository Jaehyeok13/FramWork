package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kh.spring.board.exception.BoardException;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.PageInfo;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.Pagination;
import com.kh.spring.member.model.vo.Member;



// Servlet 에 bean 등록 하는 것을 어노테이션으로 대체 한다 Controller 로 한다. -> serviceImpl 로 간다. 이동선 주입 DI 
@Controller
public class BoardController {
//	serviceImpl 로 간다. 이동선 주입 DI 인터페이스로 느슨한 관계를 위해서
	
	@Autowired
	BoardService bService; // 의존성을 높이기 위해서 오토와이어 선언
	
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
	
	// json - 1
	// 메인에 게시글 tob 보여주기
	@RequestMapping("topList.do")
	public void boardTopList(HttpServletResponse response) throws IOException {
		response.setContentType("application/json; charset=utf-8" );
		
		ArrayList<Board> list = bService.selectTopList();
		
		JSONArray jArr = new JSONArray(); // json 제공하는 배열 사용해서 담는다.
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 작성일 날자 원하는 결과로 출력하기 위해 사용
		
		for(Board b : list) {
			  JSONObject jObj = new JSONObject();
			  jObj.put("bId", b.getbId());
			  jObj.put("bTitle", b.getbTitle());
			  jObj.put("bWriter", b.getbWriter());
			  jObj.put("originalFile", b.getOriginalFile());
			  jObj.put("bCount", b.getbCount());
			  jObj.put("bCreateDate", sdf.format(b.getbCreateDate()));
		         
		         jArr.add(jObj);
					
			
		}
		
		JSONObject sendJson = new JSONObject();
		sendJson.put("list", jArr);
		
		PrintWriter out = response.getWriter();
		out.print(sendJson);
		out.flush();
		out.close();
		
	}
	
	
	// json -2
//	@RequestMapping("topList.do")
//	@ResponseBody
//	public String boardTopList() throws  IOException {
//		
//		ArrayList<Board> list = bService.selectTopList();
//		
//		// 제목 부분 한글 깨지기 때문에 인코딩 해준다.
	
//		for(Board b : list) {
//			b.setbTitle(URLEncoder.encode(b.getbTitle(), "utf-8"));
//			
//		}
//		ArrayList배열을 String 으로 받기 위해서 ObjectMapper 을 사용 해야했다.
//		ObjectMapper mapper = new ObjectMapper(); // porm.xml 가서 라이브러리 추가
//	
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		
//		mapper.setDateFormat(sdf);
//		
//		String jsonStr = mapper.writeValueAsString(list); // mapper 형변환 하여 스트링으로 넣는다.
//		
//		return jsonStr;
	
//		list 자체를 list 로 보내는 방법도 있다. 여러가지 포맷도 하고 lib 추가도 해야 하고 복잡 해진다.
//		
//	}
	
//	@RequestMapping("topList.do")
//	public void boardTopList(HttpServletResponse response) throws  IOException {
//		
//		ArrayList<Board> list = bService.selectTopList();
//		// 제목 부분 한글 깨지기 때문에 인코딩 해준다.
//		for(Board b : list) {
//			b.setbTitle(URLEncoder.encode(b.getbTitle(), "utf-8"));
//		}
	
//		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
//		gson.toJson(list, response.getWriter());
//		
//	}
	
	// 댓글 리스트 가져오기
	@RequestMapping("rList.do")
	public void getReplyList(HttpServletResponse response ,@RequestParam("bId") int bId) throws IOException {
		ArrayList<Reply> rList = bService.selectReplyList(bId); // bid 기준으로 받아 오겟다.

		for(Reply r : rList) {
		    r.setrContent(URLEncoder.encode(r.getrContent(), "utf-8"));
		}
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		gson.toJson(rList, response.getWriter());
		
	}
	
	// 댓글 등록
	@RequestMapping("addReply.do")
	@ResponseBody
	public String addReply(Reply r, HttpSession session) throws BoardException {
	
		Member loginUser = (Member)session.getAttribute("loginUser");
		String rWriter = loginUser.getId();
		
		r.setrWriter(rWriter);
		
		System.out.println(r);
		int result = bService.insertReply(r);
		
		if(result > 0) {
			return "success";
		}else {
			throw new BoardException("댓글 등록에 실패하였습니다.");
		}
		
	}
	
	
}
