<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
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
<c:set var="menu" value="직원" />
<%@ include file="../common/navbar.jsp" %>
<div class="container">
  <div class="row mb-3">
  	<div class="col-12">
  		<div class="card">
  			<div class="card-header">내 정보</div>
  			<div class="card-body">
  				<p class="card-text"><strong>${emp.firstName } ${emp.lastName }님 정보를 확인하세요.</strong></p>
  				<table class="teble table-bordered">
  					<tr>
  						<th style="width: 15%">아이디</th>
  						<th style="width: 35%">${emp.id }</th>
  						<th style="width: 15%">이름</th>
  						<th style="width: 35%">${emp.firstName } ${emp.lastName }</th>
  					</tr>
  					<tr>
  						<th style="width: 15%">이메일</th>
  						<th style="width: 35%">${emp.email }</th>
  						<th style="width: 15%">전화번호</th>
  						<th style="width: 35%">${emp.phoneNumber }</th>
  					</tr>
  					<tr>
  						<th style="width: 15%">직종</th>
  						<th style="width: 35%">${emp.job.id }</th>
  						<th style="width: 15%">입사일</th>
  						<th style="width: 35%"><fmt:formatDate value="${emp.hireDate }" pattern="yyyy년MM월dd일"/></th>		
  					</tr>
  					<tr>
  						<th style="width: 15%">급여</th>
  						<th style="width: 35%">$<fmt:formatNumber value=" ${emp.salary }" /></th>
  						<th style="width: 15%">커미션</th>
  						<th style="width: 35%">${emp.commissionPct }</th>
  					</tr>
  				</table>
  			</div>
  		</div>
  	</div>
  </div>
</div>
</body>
</html>