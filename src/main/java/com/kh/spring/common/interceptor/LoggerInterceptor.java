package com.kh.spring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


//HandlerInterceptorAdapter : 추상 클래스 우리는 이거를 오버라이딩해서 사용할 거임
public class LoggerInterceptor extends HandlerInterceptorAdapter{
   
   protected Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);
   
   @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
         throws Exception {
	   /*
	    	DispatcherServlet이 Controller를 호출하기 전(Controller로 요청이 들어가기 전)에 수행
	    	가장 마지막에 들어간 매개변수 preHandler()를 수행하고 수행될 컨트롤러 메소드에 대한 정보를 담고 있음
	    */
	  if(logger.isDebugEnabled()) {
		  logger.debug("======================== STAR ========================");
		  logger.debug(request.getRequestURI());
		  // 출력 할때 끼어 들어서 작동 하겟다.
	  }
	   
      return super.preHandle(request, response, handler);
   }
   
   @Override
   public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
         ModelAndView modelAndView) throws Exception {
	   /*
	    	컨트롤러에서 DispatchserServlet으로 리턴되는 순간에 수행 ModelAndView를 통해 작업 결과 참조 가능, 전달해온 ModelAndView 객체가 전달됨으로써
	    	컨트롤러에서 작업 후 postHandler() 에서 작업할 것이 있다면, ModelAndView를 이용하여 작업
	   */
	   if(logger.isDebugEnabled()) {
		   logger.debug("---------- view -----------");
	   }
	   
   }
   
   @Override
   public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
         throws Exception {
	   /*
	     	최종 결과를 생성하는 일을 포함한 모든 작업이 완료 된 후 수행
	     	요청 처리 중 사용한 리소스를 반환해주기에 적당
	   */
	   if(logger.isDebugEnabled()) {
		   logger.debug("=============== END ==============\n");
	   }
   }
   
   
   
}