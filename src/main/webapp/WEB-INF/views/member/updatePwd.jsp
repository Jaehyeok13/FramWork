<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${contextPath}/resources/css/member-style.css" type="text/css">
</head>
<body>

	<c:import url="../common/menubar.jsp"/>
	
	<h1 align="center">비밀번호 수정</h1>
	
	<div class="centerText">
		<form action="mPwdUpdate.do" method="post" onsubmit="return check()">
			<table>
				<tr>
					<th>현재 비밀번호</th>
					<td><input type="password" name="pwd"></td>
				</tr>
				<tr>
					<th>새 비밀번호</th>
					<td><input type="password" name="newPwd1"></td>
				</tr>
				<tr>
					<th>새 비밀번호 확인</th>
					<td><input type="password" name="newPwd2"></td>				
				<tr>
					<td colspan="2" align="center">
						<input type="submit" value="수정하기">
						<button type="button" onclick="location.href='home.do'">시작 페이지로 이동</button>
					</td>
				</tr>
			</table>
		</form>		
	</div>
	
	<script>
		
			function check(){
			var pw = $('input[name=pwd]').val().trim();
			var pw1 = $('input[name=newPwd1]').val().trim();
			var pw2 = $('input[name=newPwd2]').val().trim();

			
			if(pw.length == 0){
				alert("현재 비밀번호가 빈칸입니다.");
				$('input[name=pwd]').focus();
				return false;
			}
			
			if(pw1.length == 0){
				alert("새 비밀번호가 빈칸 입니다.");
				$('input[name=newPwd1]').focus();
				return false;
			}
			
			if(pw2.length == 0){
				alert("새 비밀번호가 빈칸 입니다.");
				$('input[name=newPwd1]').focus();
				return false;
			}
			
			
			if(pw1 != pw2){
				alert("새 비밀번호와 새 비밀번호 확인이 맞지 않습니다.");
				$('input[name=newPwd1]').val("");
				$('input[name=newPwd2]').val("");
				
				return false;
			}
			return true;
			}
	</script>
	
</body>
</html>