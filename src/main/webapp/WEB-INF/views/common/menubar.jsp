<%@ page language="java" contentType="text/html; charset=UTF-8"
	    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.nav {width:780px; margin-left:auto; margin-right:auto;}
	.menu {
		display:inline-block; background:#e4fc41; text-align:center;
		line-height:50px; width:150px; height:50px;
		margin-left:20px; margin-right:20px; border-radius:20px;
	}
	.menu:hover {background:#b0c903; font-weight: bolder; cursor:pointer;}
	a:link {color: black; text-decoration: none;}
	a:visited {color: black; text-decoration: none;}
</style>
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script> 
</head>
<body>
	
	<!-- 메뉴바는 어느 페이지든 포함하고 있을 테니 여기서 contextPath 변수 값 만들어주자 -->
	<c:set var="contextPath" value="${ pageContext.servletContext.contextPath }" scope="application"/>
	
	<h1 align="center">Finally, SPRING</h1>
	<br>
	
	<!-- 기존과 동일 하게 작업!! 이제는 바로 로그인유저가 있는 경우 없는 경우 동시에 작업하자!! -->
	<div class="loginArea" align="right">
		<c:if test="${ empty sessionScope.loginUser }">
			<form action="login.do" method="post">
				<table id="loginTable" style="text-align:center;">
					<tr>
						<td>아이디</td>
						<td><input type="text" name="id"></td>
						<td rowspan="2">
							<button id="loginBtn">로그인</button>
						</td>
					</tr>
					<tr>
						<td>비밀번호</td>
						<td><input type="password" name="pwd"></td>
					</tr>
					<tr>
						<td colspan="3">
							<button type="button" onclick="location.href='enrollView.do'">회원가입</button>
							<button type="button">아이디/비밀번호 찾기</button>
						</td>
					</tr>
				</table>
			</form>
		</c:if>
		<c:if test="${ !empty sessionScope.loginUser }">
			<h3 align="right">
				<c:out value="${ loginUser.name }님 환영합니다."/>
				<c:url var="myinfo" value="myinfo.do"/>
				<c:url var="logout" value="logout.do"/>
				<button onclick="location.href='${myinfo}'">정보보기</button>
				<button onclick="location.href='${logout}'">로그아웃</button>
			</h3>
		</c:if>
	</div>
	
	<c:url var="nlist" value="nlist.do"/>
	<c:url var="blist" value="blist.do"/>

	<div class="menubar">
		<div class="nav">
			<div class="menu"><a href="home.do">HOME</a></div>
			<div class="menu"><a href="${ nlist }">공지사항</a></div>
			<div class="menu"><a href="${ blist }">게시판</a></div>
			<div class="menu">etc</div>
		</div>
	</div>
</body>
</html>