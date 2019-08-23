package com.kh.spring.member.controller;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.kh.spring.member.model.exception.MemberException;
import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;


@SessionAttributes("loginUser") // Model에 loginUser 라는 키 값으로 객체를 추가 하면 자동으로 세션에 추가
@Controller // Controller 타입의 어노테이션을 붙여주면 빈 스캐닝을 통해 자동 등록    클래스 이므로
public class MemberController {

	@Autowired	// 의존성 주입 연결을 해두어서 객체 이름 변경 해도 문제 없음 서비스를 알아서 연결 한다.
	private MemberService mService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder; // bean 으로 등록 해야지 사용이 가능하다. 새로운 xml 파일 만들어서 등록 하겟다.
	
	/******************* 파라미터 전송 받는 방법 ********************/
	
	/*
	  	1. HttpServletRequest 를 통해 전송받기(JSP/Servlet방식)
	 		메소드의 매개변수로 HttpservletRequest를 작성하면
	 		메소드 실행 시 스프링 컨테이너가 자동으로 객체를 인자로 주입
	*/
	
	/*
	 @RequestMapping(value="login.do", method=RequestMethod.POST)
	  public String memberLogin(HttpServletRequest request) {
	   String id = request.getParameter("id"); 
	   String pwd = request.getParameter("pwd");

	  System.out.println("Id1 : " + id); System.out.println("Pwd1 : " + pwd);
	
	 return "home"; 
	 }
	 */
// ==================================================================================================		
	/*
	  2. @RequestParam 어노테이션 방식 
	  		파라밑커를 조금 더 간단하게 받아올 수 있는 방법(스프링에서 제공)
	  		(HttpServlet과 비슷하게 request.객체를 받아옴)
	  		값을 하나하나 받아 올 수 있다.
	*/
/*	@RequestMapping(value="login.do", method=RequestMethod.POST)
//	public String memberLogin(@RequestParam("id") String id, @RequestParam("pwd") String pwd) {
	public String memberLogin(
			@RequestParam(value="id", required=false, defaultValue="hello") String id,
			@RequestParam(value="pwd", required=false, defaultValue="world") String pwd) {
		System.out.println("Id2 : " + id);
		System.out.println("Pwd2 : " + pwd);
		
		return "home";
	}  */
// ==================================================================================================	
	/* 
	 	3. @RequestParam 어노테이션 생략
	 		메소드의 매개변수가 파라미터 명(name)과 동일할 때 자동으로 값을 주입하여 ㅂㄴ수에 저장 가능
	 		단 어노테이션 생략했으므로 defaultValue 와 required 설정 불가능
	 		없는 파라미터일 경우 null 값 입력
	 */
	
	/*
	 * @RequestMapping(value="login.do", method=RequestMethod.POST) public String
	 * memberLogin(String id, String pwd) { System.out.println("Id3 : " + id);
	 * System.out.println("Pwd3 : " + pwd);
	 * 
	 * return "home"; }
	 */
// ==================================================================================================		
	
	/* 
	 4. @ModelAttribute를 이용한 값 전달 방법
	 		요청 파라미터가 많을 경우 객체 타입으로 넘겨 받음
	 			이 때, 기본 생성자와 setter를 이용한 주입 방식이기 때문에 둘 중 하나라도 없으면 에러가 남
	 			
	 			== > 이런 방식을 커맨드 방식이라고 함
	 				 스프링 컨테이너가 기본 생정자를 통해 Member 를 생성하고
	 				 setter 메소드로 꺼낸 파라미터들로 값을 변경한 후에 현재 이 메소드를 호출할 때
	 				 인자로 전달하며 호출하는 방식으로 주업
	 				 (단, 반드시 name 속성 값과 필드 명이 동일해야 하고 setter 작성 규칙에 맞게 작성해야 함)
    */
/*	@RequestMapping(value="login.do", method=RequestMethod.POST)
	public String memberLogin(@ModelAttribute Member m) {
		
		System.out.println("Id4 : " + m.getId());
		System.out.println("Pwd4 : " + m.getPwd());
		
		return "home";
	}
	*/
	
// ==================================================================================================		
	
	/*
	  5. @ModelAttribute 어노테이션 생략하고 객체로 바로 전달 받는 방법
          어노테이션 생략해도 자동으로 커맨드 객체로 메핑
	 */
	
/*	@RequestMapping(value="login.do", method=RequestMethod.POST)
	public String memberLogin(Member m, HttpSession session) {
		
		System.out.println("Id5 : " + m.getId());
		System.out.println("Pwd5 : " + m.getPwd());
		
//		MemberService mService = new MemberServiceImpl();
		System.out.println(mService.hashCode());
		
		Member loginUser = mService.memberLogin(m); // service 에 만들어질거임
		System.out.println(loginUser);
		
		if(loginUser != null) {
			// 로그인 성공시 세션에 정보를 담아야하기 땜누에 세션 필요
			// 매개변수로 HttpSession 추가!!
			session.setAttribute("loginUser", loginUser);
		}
		return "home";
	}
	*/
	
//========================================================================================	
	/******** 요청 후 전달하고자 하는 데이터가 있을 경우에 대한 방법********/
	
	/*
	 	1. Model 객체를 사용하는 방법
	 		커맨드 객체로 Model을 사용하게 되면 뷰로 전달하고자 하는 데이터를 맵 형식(key, value)으로 담을 때 사용
	 		scope는 request이며 서블릿에서 사용하던 requesetScope 와 비슷
	 		
	 		model 객체 : request와 response의 역할을 하는 스프링 객체
			ModelAndView : viewResolver에 view의 값을 직접 전달하는 객체
	 
	 */
	
/*	@RequestMapping(value="login.do", method=RequestMethod.POST)
	public String memberLogin(Member m, Model model, HttpSession session) {
		
		System.out.println("Id5 : " + m.getId());
		System.out.println("Pwd5 : " + m.getPwd());
		
		System.out.println(mService.hashCode());
		
		Member loginUser = mService.memberLogin(m); // service 에 만들어질거임
		System.out.println(loginUser);
		
		if(loginUser != null) {
			// 로그인 성공시 세션에 정보를 담아야하기 땜누에 세션 필요
			// 매개변수로 HttpSession 추가!!
			session.setAttribute("loginUser", loginUser);
			return "home";
		}else {
			model.addAttribute("msg","로그인에 실패하였습니다.");
			return "common/errorPage";
		}
	}
	*/
	
//=================================================================================
	/*
	 	2. ModelAndView 객체 사용하는 방법
	 		위에서 Model 은 절달고하자 하는 데이터를 맵 형식으로 담는 공간이라면
	 		View는 requestDispatcherServlet 처럼 forward할 뷰 페이지 정보를 담은 객체라고 할 수 있다.
	 		
	 		ModelAndView 는 이 두 가지를 합쳐놓은 객체이며
	 		위처럼 Model 은 따로 사용 가능하ㅈ미나 View는 따로 사용 불가능 하다.
	 		
	 		커맨드 객체로 ModelAndView 를 사용하여 전달하고자 하는 데이터와 뷰를 set함
	 */
	
	
/*	@RequestMapping(value="login.do", method=RequestMethod.POST)
	public ModelAndView memberLogin(Member m, ModelAndView mv, HttpSession session) {
		// 뷰랑 넘겨줄 값이 같이 들어가 있는?
		
		Member loginUser = mService.memberLogin(m); // service 에 만들어질거임
		
		if(loginUser != null) {
			// 로그인 성공시 세션에 정보를 담아야하기 땜누에 세션 필요
			// 매개변수로 HttpSession 추가!!
			session.setAttribute("loginUser", loginUser);
			mv.setViewName("home");
		}else {
			mv.addObject("msg","로그인에 실패하였습니다.");
			mv.setViewName("common/erroPage");
		}
		
		return mv;
	}
	*/
	
	
	
	// 로그아웃 용 컨트롤러1
/*	@RequestMapping("logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		return "home";
	}
*/	
	/* 
	 	3. session에 저장할 때 @SessionAttributes 사용하기
	 		Model에 Attribute가 추가될 때 자동으로 키 값을 찾아 세션에 등록하는 기능 제공
	 */
/*	@RequestMapping(value="login.do", method=RequestMethod.POST)
	public String memberLogin(Member m, Model model) {
		Member loginUser = mService.memberLogin(m);
		
		if(loginUser != null) {
			model.addAttribute("loginUser",loginUser);
			
			return "home";
		}else {
			throw new MemberException("로그인에 실패하였습니다.");
		}
	}
*/	
	// 로그아웃 용 컨트롤러2
	@RequestMapping("logout.do") // SessionStatus 상태를 관리해주는 놈 커맨드 객체로 
	public String logout(SessionStatus status) {
		
		// session 정보를 없애 준다.
		status.setComplete();
		
		return "home";
	}
	
	
	// 회원 가입
	// 회원 가입 페이지 이동
	@RequestMapping("enrollView.do")
	public String enrollView() {
		return "member/memberJoin";
	}
	
	// 회원가입 창에서 join(회원가입 버튼 눌릴때 작동)
	// 여러개 중 하나를 
	@RequestMapping("minsert.do")
	public String memberInsert(@ModelAttribute Member m, @RequestParam("post") String post , @RequestParam("address1") String address1, @RequestParam("address2") String address2) {
		// 이안에 뭐가 들어가는 게 중요하다.  한번에 받아 올수 있는 것을 객체 로 받아오고 필드 값이랑 매칭후 들어 갈 수 없는것을  @RequestParam 을 이용해서 값을 받아온다.
		
		System.out.println(m);
		
		/* 1. 결과 값을 받아오면 한글이 깨짐
		 	스프링에서 제공하는 필터를 이용해서 요청 시 전달 받는 값에 한글이 있을 경우 인코딩 하는 것 추가  web.xml 에 추가 해준다.
			
		   2. 비밀번호 평문
		   		bcrypt : 스프링 시큐리티 모듈에서 제공하는 암호화 방식
		   		
		   		solting 이라고 해서 할 때마다 암호화 된 값을 계속 변경 해준다.
		   		
		   		pom.xml 가서 처리한다. lib 러브 3개 추가 한다음
		   		
		*/
		String encPwd = bcryptPasswordEncoder.encode(m.getPwd()); // 이렇게 암호화 된 비밀 번호 값이 들어감
		m.setPwd(encPwd);
		
		System.out.println(m);
		
		m.setAddress(post + "/" + address1 + "/" + address2); 
		
		int result = mService.insertMember(m);
		
		if(result > 0) {
			return "home"; // 회원가입 완료시 가는 페이지 홈
		}else {
			throw new MemberException("회원 가입에 실패 하였습니다.");
		}
		
	}
	
	//암호화  후 로그인
	@RequestMapping(value="login.do", method=RequestMethod.POST)
	public String memberLogin(Member m, Model model) {
		
/*		String encPwd = bcryptPasswordEncoder.encode(m.getPwd());
		// 어떤한 문자열이 솔팅 값때문에 계속 다르기 때문에 매칭이 되지 않는다.
		m.setPwd(encPwd);
		
		Member loginUser = mService.memberLogin(m);
		
		if(loginUser != null) {
			model.addAttribute("loginUser",loginUser);
			
			return "home";
		}else {
			model.addAttribute("msg","로그인에 실패하였습니다.");
			throw new MemberException("로그인에 실패하였습니다.");
		}
		*/
		Member loginUser = mService.memberLogin(m);
		
		// 애내가 해결 못하니간 자체적으로 메소드를 제공한걸 이용 한다.
		if(bcryptPasswordEncoder.matches(m.getPwd(), loginUser.getPwd())) {
		// boolean org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.matches(CharSequence rawPassword, String encodedPassword)
			model.addAttribute("loginUser", loginUser);
		}else {
			throw new MemberException("로그인에 실패 하였습니다.");
		}
		return "home";
	}
	// 마이 페이지 이동
	@RequestMapping("myinfo.do")
	public String mypage() {
		return "member/myinfo";
	}
	
	// 마이 페이지에서 수정하기 누를 경우 업데이트 폼으로 이동
	@RequestMapping("mupdateView.do")
	public String updatePage() {
		return "member/mupdateView";
	}
	
	@RequestMapping("mupdate.do")
	public String updateMember(Model model, @ModelAttribute Member m, @RequestParam("post") String post,  @RequestParam("address1") String address1, @RequestParam("address2") String address2) {
		System.out.println(m);
		
		m.setAddress(post + "/" + address1 + "/" + address2); 
		
		int result = mService.updateMember(m);
				
		if(result > 0) {
			model.addAttribute("loginUser", m);
			return "member/myinfo"; // 회원가입 완료시 가는 페이지 홈
		}else {
			throw new MemberException("회원 수정에 실패 하였습니다.");
		}
		
	}
	
	// 비밀번호 변경 페이지
	@RequestMapping("mpwdUpdateView.do")
	public String updatePwPage() {
		return "member/updatePwd";
	}
	
	// 비번 변경
	@RequestMapping("mPwdUpdate.do")
	public String updatePwd(HttpSession session, Model model,  @RequestParam("pwd") String pwd, @RequestParam("newPwd1") String newPwd1) {
		
		Member m = (Member)session.getAttribute("loginUser");
		
		// 화면 값이랑 DB 값이 일치 할 경우
		if(bcryptPasswordEncoder.matches(pwd, m.getPwd())) {
			// boolean org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.matches(CharSequence rawPassword, String encodedPassword)
			m.setPwd(bcryptPasswordEncoder.encode(newPwd1));
			
			int result = mService.updatePwd(m);
			
			if(result > 0) {
			
				return "home";	
		
			}else {
				throw new MemberException("현재 비밀번호가 입력하신 번호와 맞지 않습니다.");
			}
		
		}else {
				throw new MemberException("비밀번호가 틀립니다. 다시 입력하세요.");
			}
	}
	
	// 회원 탈퇴
	@RequestMapping("mdelete.do")
	public String deleteMember(@RequestParam("id") String id, SessionStatus status) {
		
		int result = mService.deletMember(id);
		
		if(result > 0) {
		//	status.setComplete();
		//	return "home";
			
			return "redirect:logout.do";
		}else {
			throw new MemberException("회원탈퇴 도중 오류 발상함");
		}
	}
	
}
