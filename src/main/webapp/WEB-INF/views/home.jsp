<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
<style>
#tb {
	margin: auto;
	width: 700px;
}
</style>

</head>
<body>
	<c:import url="common/menubar.jsp" />

	<h1 align="center">게시글 TOP5 목록</h1>
	<table id="tb" border="1">
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>날짜</th>
				<th>조회수</th>
				<th>첨부파일</th>
			</tr>
		</thead>
		<tbody></tbody>
	</table>
	<script>
	function topList(){
		$.ajax({
			url: "topList.do",
			/*  dataType: "json",  */
			success: function(data){
				$tableBody = $("#tb tbody");
				$tableBody.html("");
				
				// json -1
				for(var i in data.list){
					var $tr = $("<tr>");
					var $bId = $("<td>").text(data.list[i].bId);
					var $bTitle = $("<td>").text(data.list[i].bTitle);
					var $bWriter = $("<td>").text(data.list[i].bWriter);
					var $bCreateDate = $("<td>").text(data.list[i].bCreateDate);
					var $bCount = $("<td>").text(data.list[i].bCount);
					var $bFile = $("<td>").text(" ");
					
					if(data.list[i].originalFile != null){
						$bFile = $("<td>").text("◎");
					}
					$tr.append($bId);
					$tr.append($bTitle);
					$tr.append($bWriter);
					$tr.append($bCreateDate);
					$tr.append($bCount);
					$tr.append($bFile);
					
					$tableBody.append($tr);
					
				} 
				
//========================================================================================		
			// sjon -2				
		// gson = 차이점은 1번가 다르게 String 자체로 보내기 때문에 mapper 형변환 한 후에  다시 String 인코딩을 해주는 공통점이 있으며 차이점은 json 코드를 더욱 간략 하게 만들어 준다.
		// jsp 는 똑같지만 controller 에서 차이점을 볼수 있음
		/* 	for(var i in data){
					var $tr = $("<tr>");
					var $bId = $("<td>").text(data[i].bId);
				// 	var $bTitle = $("<td>").text(data[i].bTitle); 
					var $bTitle = $("<td>").text(decodeURIComponent(data[i].bTitle.replace(/\+/g, " ")));
					var $bWriter = $("<td>").text(data[i].bWriter);
					var $bCreateDate = $("<td>").text(data[i].bCreateDate);
					var $bCount = $("<td>").text(data[i].bCount);
					var $bFile = $("<td>").text(" ");
					
					
					if(data[i].originalFile != null){
						$bFile = $("<td>").text("◎");
					}
					$tr.append($bId);
					$tr.append($bTitle);
					$tr.append($bWriter);
					$tr.append($bCreateDate);
					$tr.append($bCount);
					$tr.append($bFile);
					
					$tableBody.append($tr);
					
				} */
	
//========================================================================================				
				// gson
				
				/* for(var i in data){
					var $tr = $("<tr>");
					var $bId = $("<td>").text(data[i].bId);
				//	var $bTitle = $("<td>").text(data[i].bTitle);
				var $bTitle = $("<td>").text(decodeURIComponent(data[i].bTitle.replace(/\+/g, " ")));
					var $bWriter = $("<td>").text(data[i].bWriter);
					var $bCreateDate = $("<td>").text(data[i].bCreateDate);
					var $bCount = $("<td>").text(data[i].bCount);
					var $bFile = $("<td>").text(" ");
					
					
					if(data[i].originalFile != null){
						$bFile = $("<td>").text("◎");
					}
					$tr.append($bId);
					$tr.append($bTitle);
					$tr.append($bWriter);
					$tr.append($bCreateDate);
					$tr.append($bCount);
					$tr.append($bFile);
					
					$tableBody.append($tr);
					
				} */
				
			}
		});
	}
	// 5초마다 갱신 하는 js
	$(function(){
		topList();
		
		setInterval(function(){
			topList();
		},5000);
	});
</script>

</body>
</html>
