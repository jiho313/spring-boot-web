<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>애플리케이션</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
</head>
<body>
<c:set var="menu" value="로그인" />
<%@ include file="../common/navbar.jsp" %>
<div class="container">
	<div class="row mb-3">
		<div class="col-12">
			<div class="card">
				<div class="card-header">로그인</div>
				<div class="card-body">
					<form action="/emp/login" method="post">
						<div class="form-group mb-3">
							<label class="form-label">이메일</label>
							<input type="text" class="form-control" name="email"/>
						</div>
						<div class="form-group mb-3">
							<label class="form-label">비밀번호</label>
							<input type="password" class="form-control" name="password"/>
						</div>
						<div class="text-end">
							<button type="submit" class="btn btn-primary btn-sm">로그인</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>