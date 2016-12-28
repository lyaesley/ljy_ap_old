<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head lang = "ko">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
</head>
<body>
	<h2>${league} 경기 결과</h2>
	<select id="resultOp">
		<option value="<c:url value='/soccer/${league }'/>">선 택</option>
		<option value="<c:url value='/soccer/${league }'/>">전체 결과</option>
		<option value="<c:url value='/soccer/${league }/home'/>">홈 경기 결과</option>
		<option value="<c:url value='/soccer/${league }/away'/>">어웨이 경기 결과</option>
	</select>
	<table class="board_list" width="100%" style="text-align:center;">
		<colgroup>
			<col width="2%"/>
			<col width="5%"/>
			<col width="2%"/>
			<col width="2%"/>
			<col width="2%"/>
			<col width="2%"/>
			<col width="2%"/>
			<col width="2%"/>
			<col width="2%"/>
			<col width="2%"/>
			
			<col width="2%"/>
			
			<col width="2%"/>
			<col width="2%"/>
			<col width="2%"/>
			<col width="2%"/>
			<col width="2%"/>
			<col width="2%"/>
			<col width="2%"/>
			<col width="2%"/>
			
			<col width="2%"/>
			
			<col width="2%"/>
			<col width="2%"/>
			<col width="2%"/>
			<col width="2%"/>
			<col width="2%"/>
			<col width="2%"/>
			<col width="2%"/>
			<col width="2%"/>
		</colgroup>
		<thead>
			<tr>
				<th colspan="10">LEAGUE</th>
				<th colspan="9">HOME MATCH</th>
				<th colspan="9">AWAY MATCH</th>
			</tr>
		
			<tr>
				<th scope="col">순위</th>
				<th scope="col">팀</th>
				<th scope="col">경기수</th>
				<th scope="col">승점</th>
				<th scope="col">승</th>
				<th scope="col">무</th>
				<th scope="col">패</th>
				<th scope="col">득점</th>
				<th scope="col">실점</th>
				<th scope="col">득실차</th>
				
				<th scope="col"></th>
				<!-- 홈 -->
				<th scope="col">경기수</th>
				<th scope="col">승점</th>
				<th scope="col">승</th>
				<th scope="col">무</th>
				<th scope="col">패</th>
				<th scope="col">득점</th>
				<th scope="col">실점</th>
				<th scope="col">득실차</th>
				
				<th scope="col"></th>
				<!-- 어웨이 -->
				<th scope="col">경기수</th>
				<th scope="col">승점</th>
				<th scope="col">승</th>
				<th scope="col">무</th>
				<th scope="col">패</th>
				<th scope="col">득점</th>
				<th scope="col">실점</th>
				<th scope="col">득실차</th>
			</tr>
		</thead>
		<tbody>
		
		<c:choose>
			<c:when test="${fn:length(list) > 0 }">
				<c:forEach items="${list }" var="row" varStatus="status">
					<tr>
						<td>${status.count }</td>
						<td><a href="<c:url value='/soccer/${league}/${row.teamName }'/>">${row.teamName }</a></td>
						<td>${row.matchCount }</td>
						<td>${row.point }</td>
						<td>${row.win }</td>
						<td>${row.draw }</td>
						<td>${row.lose }</td>
						<td>${row.goal }</td>
						<td>${row.goalLoss }</td>
						<td>${row.goalDiff }</td>
						
						<td></td>
						
						<td>${home[status.index].teamName },${home[status.index].matchCount }</td>
						<td>${home[status.index].point }</td>
						<td>${home[status.index].win }</td>
						<td>${home[status.index].draw }</td>
						<td>${home[status.index].lose }</td>
						<td>${home[status.index].goal }</td>
						<td>${home[status.index].goalLoss }</td>
						<td>${home[status.index].goalDiff }</td>
						
						<td></td>
						
						<td>${away[status.index].teamName },${away[status.index].matchCount }</td>
						<td>${away[status.index].point }</td>
						<td>${away[status.index].win }</td>
						<td>${away[status.index].draw }</td>
						<td>${away[status.index].lose }</td>
						<td>${away[status.index].goal }</td>
						<td>${away[status.index].goalLoss }</td>
						<td>${away[status.index].goalDiff }</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="4" align="center">조회된 결과가 없습니다.</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</tbody>
	</table>		
</body>
<script>
	$(document).ready(function(){
		$("#resultOp").change(function(){
			var url = $('#resultOp option:selected').val();
			$(location).attr('href',url);
		});
		
	});
</script>
</html>