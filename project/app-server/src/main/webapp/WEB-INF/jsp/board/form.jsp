<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset='UTF-8'>
  <title>국군인트라넷</title>
  <link rel="stylesheet" href="../../../css/board/form.css">
  <jsp:include page="../header.jsp"/>
</head>
<body>

<div class="container">
  <h1 class="form_title">게시글 등록</h1>
  <form action='add' method='post' enctype='multipart/form-data'>
    제목 <input type='text' name='title'><br>
    내용 <textarea name='content'></textarea><br>
    파일 <input type='file' name='files' multiple><br>
    <input type='hidden' name='category' value='${param.category}'>
    <div class="button_container">
      <button class="btn">등록</button>
      <a href='list?category=${param.category}'>
        <button class="btn">뒤로</button>
      </a>
    </div>
  </form>

</div>
<jsp:include page="../footer.jsp"/>
</body>
</html>
