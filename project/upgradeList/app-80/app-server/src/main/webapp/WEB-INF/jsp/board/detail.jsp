<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"
        errorPage="/WEB-INF/jsp/error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="refresh" value="2;url=list.jsp?category=${param.category}" scope="request"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset='UTF-8'>
  <title>국군인트라넷</title>
  <link rel="stylesheet" href="/css/board/detail.css">
</head>
<body>

<jsp:include page="../header.jsp"/>

<h1>게시글</h1>

<c:if test="${empty board}">
  <p>해당 번호의 게시글이 없습니다!</p>
</c:if>

<c:if test="${not empty board}">
  <form action='/app/board/update' method='post' enctype='multipart/form-data'>
    <table border='1'>
      <tr>
        <th style='width:120px;'>번호</th>
        <td style='width:300px;'><input type='text' name='no' value='${board.no}' readonly></td>
      </tr>
      <tr>
        <th>제목</th>
        <td><input type='text' name='title' value='${board.title}'></td>
      </tr>
      <tr>
        <th>내용</th>
        <td><textarea name='content' style='height:200px; width:400px;'>${board.content}</textarea>
        </td>
      </tr>
      <tr>
        <th>작성자</th>
        <td>${board.writer.name}</td>
      </tr>
      <tr>
        <th>조회수</th>
        <td>${board.viewCount}</td>
      </tr>
      <tr>
        <th>등록일</th>
        <td><fmt:formatDate value="${board.createdDate}" pattern="yyyy-MM-dd"/></td>
      </tr>
      <tr>
        <th>첨부파일</th>
        <td>
          <c:forEach items="${board.attachedFiles}" var="file">
            <a href='https://kr.object.ncloudstorage.com/bitcamp-nc7-bucket-01/board2/${file.filePath}'>${file.filePath}</a>
            [<a href='/app/board/fileDelete/fileNo=${file.no}'>삭제</a>]<br>
          </c:forEach>
          <input type='file' name='files' multiple>
        </td>
      </tr>
    </table>

    <div>
      <button>변경</button>
      <button type='reset'>초기화</button>
      <a href='/app/board/delete?category=${board.category}&no=${board.no}'>삭제</a>
      <a href='/app/board/list?category=${board.category}'>목록</a>
    </div>
  </form>
</c:if>

<jsp:include page="../footer.jsp"/>

</body>
</html>













