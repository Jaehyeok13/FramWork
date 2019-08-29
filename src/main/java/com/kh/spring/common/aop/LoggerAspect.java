package com.kh.spring.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerAspect {
	private Logger logger = LoggerFactory.getLogger(LoggerAspect.class);
	
	public Object loggerAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		// 넣기 위한 코드를 입력 한것임
		
		Signature signature = joinPoint.getSignature(); // 메소드에 대한 정보가 담겨잇는 메소드
		// signature : 지금 내가 가고 있는 대상 Object 메소드가 나오게 된다.  
		// 만약 Controller 에서 메모 메소드를 불러 온다고 하면 메모 메소드 정보를 가져온다. 반환 값이랑 어떤 클래스안의 어떠한 메소드를 불러온다.
		
		// ex : signature : ModelAndView com.kh.spring.memo.controller.MemoController.memo();
		
		logger.debug("signature = " + signature);
		
		String type = signature.getDeclaringTypeName(); // ~ 에 전시되어 있는 건데 / 물어보는 것
		
		// type : com.kh.spring.memo.controller.Memocontroller
		logger.debug("type = " + type);
		
		String methodName = signature.getName();
		// methodeName : memo(); 메소드 이름
		
		logger.debug("methodName = " + methodName);
		
		
		/**** 여태 까지 어떠한 곳에서 target 정보만 추출 해오는 것*****/
		
		String componentName = "";
		if (type.indexOf("Controller") > -1) {
			componentName = "Controller : ";
		} else if (type.indexOf("Service") > -1) {
			componentName = "ServiceImpl : ";
		} else if (type.indexOf("DAO") > -1) {
			componentName = "DAO : ";
		}
		
		// 콘솔에 나올거 입력한다.
//		logger.debug("[Befor]" + componentName + type + "." + methodName + "()");
		
		
//		return joinPoint.proceed();
		
		logger.debug("[Before]" + componentName + type + "." + methodName + "()");
		Object obj = joinPoint.proceed();
		logger.debug("[After]" + componentName + type + "." + methodName + "()");
		return obj;
		
		
	}
	// web.xml 에 Contect 를 등록해줘야 한다.
}
























