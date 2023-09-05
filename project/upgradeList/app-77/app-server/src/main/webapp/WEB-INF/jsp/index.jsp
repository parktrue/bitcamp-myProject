<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8" %>

<%@ page import="bitcamp.myapp.vo.Soldier" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset='UTF-8'>
  <title>국군인트라넷</title>
  <link rel="stylesheet" href="../../css/index.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class='container'>
  <img src='/marinedog.png' alt='병력 관리' class='logo' height="100px">
  <h1>인사정보체계</h1>
  <a href='/app/soldier/list' class='btn'>병사 목록</a>
  <a href='/app/board/list?category=1' class='btn'>게시판</a>
  <a href='/app/board/list?category=2' class='btn'>독서록</a>

  <%
    Soldier loginUser = (Soldier) request.getSession().getAttribute("loginUser");
    if (loginUser == null) { %>
  <a href='/app/auth/form' class='btn'>로그인</a>
  <% } else { %>
  <a href='/app/auth/logout' class='btn'>로그아웃</a>
  <div class='login-info'>로그인 중: ${loginUser.militaryNumber}(${loginUser.name}, ${loginUser.rank})</div>
  <% } %>
</div>

<jsp:include page="footer.jsp"/>
</body>
</html>
