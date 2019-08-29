<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
	#memoForm{width: 420px; margin: auto;}
	.table{text-align: center; margin: auto;}
	.table th, td{padding: 5px;}
	
	.de{
		cursor:pointer;
	}
</style>
<title>Insert title here</title>
</head>
<body>
	<c:import url="../common/menubar.jsp"/>
	
	<br><br>
	
    <form action="${contextPath}/memo/insertMemo.do" method="post" id="memoForm">
        <input type="text" name="memo" placeholder="메모" required/>&nbsp;
        <input type="password" name="password" maxlength="4" placeholder="비밀번호" required/>&nbsp;
        <button type="submit">저장</button>
    </form>
    
    <br><br>
    
    <!-- 메모목록 -->
    <table class="table">
        <tr>
            <th>번호</th>
            <th width="500px;">메모</th>
            <th width="300px;">날짜</th>
            <th>삭제</th>
        </tr>
        <c:forEach var="m" items="${ list }"> 
        	<tr>
        		<td>${ m.MEMONO }</td>
        		<td>${ m.MEMO }</td>
        		<td>${ m.MEMODATE }</td>
        		<td><span class="de" id="${ m.MEMONO }"> 삭제</span></td>
        	</tr>
        </c:forEach>
        
    </table>
	
    <script>
    	$(function(){
    		$(".de").on("click", function(){
    		/* 	if(confirm('삭제 하시겠습니까?')){ 
    				
    				}  */
				var no = $(this).attr('id'); // 댓글 번호 받고
				
				
    				
    				
    		//	location.href="${contextPath}/memo/delete.do";
    			
    		});
    	});
    </script>
    
</body>
</html>