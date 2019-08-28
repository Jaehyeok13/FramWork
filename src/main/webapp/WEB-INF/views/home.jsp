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
*{box-sizing:board-box;}
	 .video-film {box-shadow: rgba(0, 7, 15, 0.7) 0 0 0 9999px; z-index: 100;}
	.video-background {
		background: #000; position: fixed;
		top: 0; right: 0; bottom: 0; left: 0; z-index: -99;
	}
	.video-foreground, .video-background iframe {
		position: absolute; top: 0; left: 0;
		width: 100%; height: 100%; pointer-events: none;
	}
	@media ( min-aspect-ratio : 16/9) {
		.video-foreground {height: 300%; top: -100%;}
	}
	@media ( max-aspect-ratio : 16/9) {
		.video-foreground {width: 300%; left: -100%;}
	}
</style>

</head>
<body>
	<c:import url="common/menubar.jsp" />
	
	<script>
		$(function(){
			var msg = "<%= request.getAttribute("msg") %>";
			if(msg != "null"){
				alert(msg);
			}
		});
	</script>
	
	

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
<script type="text/javascript" src="resources/js/typeit.min.js"></script><!-- .js 넣기 -->
	<script type="text/javascript">
		$(function(){
			var str1 = ["본 사이트는 포르쉐 홍보 영상입니다."];
			var str2 = ["누구나 할 만들 수 있는<br>_나만의 도시락과 <br>_도시락 자랑 게시판 <br>_지금 바로 함께 하세요!!"];
			var str3 = ["Hey, you!!<br>_Don't be hesitate,<br>_Right Now Start!!"];
			
			$('.type_text').typeIt({
				strings:str1,
				html:true,
				autoStart:true,
				loop:true,
				typeSpeed:100
			})
			.tiPause(1500)
			.tiDelete(130)
			.tiType(str2)
			.tiPause(1700)
			.tiDelete(130)
			.tiType(str3)
			.tiPause(1700)
			.tiEmpty(); 
		});
	</script>

</body>
</html>
