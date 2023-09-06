<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
</head>
<body>
<!-- 상단 헤더 영역 시작 -->
<header class="top-header">
  <!-- 왼쪽 이미지 영역 -->
  <div class="logo_left">
    <img src="/mil_img.png">
  </div>
  <!-- 가운데 텍스트 및 배경 이미지 -->
  <div class="center-content">
    <h1>인트라넷</h1>
  </div>
  <!-- 오른쪽 이미지 영역 -->
  <div class="logo_right">
    <img src="/mil_img3.png">
  </div>
</header>
<!-- 상단 헤더 영역 끝 -->

<!-- 하단 헤더 영역 시작 -->
<header class="bottom-header">
  <nav class="nav-menu">
    <!-- 메뉴 영역 시작 -->
    <ul class="menu">
      <li><a href="/">홈</a></li>
      <li><a href="/admin/soldier/list">인사정보체계</a></li>
      <li><a href="/app/board/list?category=1">공지사항</a></li>
      <li><a href="/app/board/list?category=2">게시판</a></li>
      <c:if test="${empty sessionScope.loginUser}">
        <li><a href='/app/auth/form'>로그인</a></li>
      </c:if>
      <c:if test="${not empty sessionScope.loginUser}">
        <li><a href='/app/auth/logout'>로그아웃</a></li>
      </c:if>
      <!-- 추가적인 메뉴 아이템을 원하는 만큼 추가할 수 있습니다 -->
    </ul>
    <!-- 메뉴 영역 끝 -->
  </nav>
</header>
<!-- 하단 헤더 영역 끝 -->

<!-- 나머지 내용 -->
</body>
</html>
