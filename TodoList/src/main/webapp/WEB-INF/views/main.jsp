<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Todo List</title>

  <link rel="stylesheet" href="/resources/css/main.css">
</head>
<body>
  <h1>Todo List</h1>

  <h3>전체 Todo 개수 : ${fn:length(todoList)} / 완료된 Todo 개수 : ${countComplete}</h3>

  <hr>

  <h4>할 일 추가</h4>
  <form action="/todo/add" method="POST" id="addForm">
    <div>
      제목 : <input type="text" name="title">
    </div>

    <br>

    <div>
      <textarea name="detail" rows="3" cols="50" placeholder="상세 내용"></textarea>
    </div>

    <br>

    <button>추가</button>
  </form>

  <hr>

  <%-- 할 일 목록 출력 --%>
  <table id="todoList" border="1">
    <thead>
      <tr>
        <th>번호</th>
        <th>할 일 제목</th>
        <th>완료 여부</th>
        <th>등록 날짜</th>
      </tr>
    </thead>
  
    <tbody>
      <c:forEach items="${todoList}" var="todo" varStatus="vs">
        <tr>
          <th>${vs.count}</th>

          <td>
            <%-- 제목 클릭 시
              인덱스 번호를 이용하여 todoList의
              인덱스 번째 요소 내요을 조회하기
              (쿼리스트링 이용 : 주소?K=V&K=V&...)
            --%>
            <a href="/todo/detail?todoNo=${todo.todoNo}">${todo.title}</a>
          </td>

          <th>
            <c:if test="${todo.complete == 1}">O</c:if>
            <c:if test="${todo.complete == 0}">X</c:if>
          </th>

          <td>${todo.regDate}</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <%-- session 범위에 message가 있을 경우(존재하는 경우) --%>
  <c:if test="${not empty sessionScope.message}" >
    <script>
      alert("${message}");
      // JSP 해석 우선순위
      // 1순위 : Java(EL/JSTL)
      // 2순위 : Front(HTML, CSS, JS)
    </script>

    <%-- messasge를 한번만 출력하고 제거 --%>
    <c:remove var="message" scope="session" />
  </c:if>

  <script src="/resources/js/main.js"></script>
</body>
</html>