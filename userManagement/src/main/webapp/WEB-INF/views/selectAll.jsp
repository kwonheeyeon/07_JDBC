<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>사용자 목록 조회</title>
</head>
<body>
  <table border="1">
    <thead>
    <tr>
      <th>회원번호</th>
      <th>아이디</th>
      <th>비밀번호</th>
      <th>이름</th>
      <th>등록일</th>
    </tr>
    </thead>

    <tbody>
    <c:forEach items="${userList}" var="user">
      <tr>
        <td>${user.userNo}</td>
        <td>${user.userId}</td>
        <td>${user.userPw}</td>
        <td>${user.userName}</td>
        <td>${user.enrollDate}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</body>
</html>