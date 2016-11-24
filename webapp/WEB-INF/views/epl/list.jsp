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
</head>
<body>
	<h2>게시판 목록</h2>
	<table class="board_list">
		<colgroup>
			<col width="*%"/>
			<col width="5%"/>
			<col width="5%"/>
			<col width="5%"/>
			<col width="5%"/>
			<col width="5%"/>
			<col width="5%"/>
			<col width="5%"/>
			<col width="5%"/>
		</colgroup>
		<thead>
			<tr>
				<th scope="col">팀</th>
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
				<c:forEach items="${list }" var="row">
					<tr>
						<td>${row.teamName }</td>
						<td>${row.matchCount }</td>
						<td>${row.point }</td>
						<td>${row.win }</td>
						<td>${row.draw }</td>
						<td>${row.lose }</td>
						<td>${row.goal }</td>
						<td>${row.goalLoss }</td>
						<td>${row.goalDiff }</td>
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
</html>