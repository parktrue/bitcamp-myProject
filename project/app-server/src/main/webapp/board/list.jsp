<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"
        errorPage="/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset='UTF-8'>
  <title>게시글</title>
  <link rel="stylesheet" href="list.css"/>
</head>
<body>

<jsp:include page="../header.jsp"/>
<link rel="stylesheet" href="../header.css">

<div class="container" style='margin:5px;'>
  <h1 class="list_title">게시글 목록</h1>

  <table border='1'>
    <thead>
    <tr>
      <th>번호</th>
      <th>제목</th>
      <th>작성자</th>
      <th>조회수</th>
      <th>등록일</th>
      <th>동작</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="board">
      <tr>
        <td>
            ${board.no}
        </td>
        <td><a href='/board/detail?category=${board.category}&no=${board.no}'>
            ${board.title.length() > 0 ? board.title : "제목없음"}
        </a>
        </td>
        <td>${board.writer.name}
        </td>
        <td>${board.viewCount}
        </td>
        <td>${simpleDateFormatter.format(board.createdDate)}
        </td>
        <td>
          <a href='/board/delete?category=${board.category}&no=${board.no}'>삭제</a>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
  <div class="btn_style">
    <a href='/board/form.jsp?category=${param.category}' class="btn">새 글</a>
    <a href='/' class="btn">메인</a>
  </div>
</div>

<jsp:include page="../footer.jsp"/>
<link rel="stylesheet" href="../footer.css">

</body>
</html>
