<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
   <title>애플리케이션</title>
   <meta charset="utf-8">
   <meta name="viewport" content="width=device-width, initial-scale=1">
   <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
   <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
</head>
<body>
<c:set var="menu" value="부서" />
<%@ include file="../common/navbar.jsp" %>
<div class="container">
	<div class="row mb-3">
		<div class="col-3">
			<div class="card">
				<div class="card-header">부서 목록</div>			
				<div class="card-body">
					<div class="list-group overflow-auto" id="dept-list" style="max-height: 500px;">
						<c:forEach var="dept" items="${depts }">
						<a href="detail?id=${dept.id }" class="list-group-item list-group-item-action" id="dept-${dept.id }" onclick="getDeptDetail(event, ${dept.id})">${dept.name }</a>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	function getDeptDetail(event, deptId) {
		event.preventDefault();
		
		// 모든 a태그를 선택한다. 배열을 반환 forEach 사용가능
		let elements = document.querySelectorAll("#dept-list a");
		// elements.forEach(el => el.classList.remove('active'));
		elements.forEach(function(el, index) {
			el.classList.remove('active');
		})
		
		let clickedElement = document.querySelector("#dept-" + deptId);
		clickedElement.classList.add('active');
		
		let xhr = new XMLHttpRequest();
		// 메소드를 실행하는 것이 아니라, xhr 프로퍼티에 이벤트 핸들러 함수를 등록하는 것
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4 && xhr.status == 200) {
				let text = xhr.responseText;
				alert(text);
			}
		}
		xhr.open("get", "detail?id=" + deptId);
		xhr.send();
	}
</script>
</body>
</html>