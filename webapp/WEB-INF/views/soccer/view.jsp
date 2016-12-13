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
	<h2>${team } 경기 결과</h2>
	<table class="board_list">
		<colgroup>
			<col width="5%"/>
			<col width="*%"/>
			<col width="*%"/>
			<col width="5%"/>
			<col width="10%"/>
			<col width="10%"/>
		</colgroup>
		<thead>
			<tr>
				<th scope="col">라운드</th>
				<th scope="col">홈팀</th>
				<th scope="col">어웨이팀</th>
				<th scope="col">승리팀</th>
				<th scope="col">홈팀득점(전반)</th>
				<th scope="col">어웨이팀득점(전반)</th>
			</tr>
		</thead>
		<tbody>
		
		<c:choose>
			<c:when test="${fn:length(list) > 0 }">
				<c:forEach items="${list }" var="row" varStatus="status">
					<tr>
						<td>${status.count }</td>
						<td>${row.hometeam }</td>
						<td>${row.awayteam }</td>
						<td>${row.ftr }</td>
						<td>${row.fthg } (${row.hthg })</td>
						<td>${row.ftag } (${row.htag })</td>
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