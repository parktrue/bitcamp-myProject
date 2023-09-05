<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset='UTF-8'>
  <form action='add' method='post' enctype='multipart/form-data'>
    <title>인사정보쳬계</title>
    <jsp:include page="../header.jsp"/>
    <link rel="stylesheet" href="../../../css/soldier/form.css">
</head>
<body>
<div class='container'>
  <h1>병사 등록</h1>
  <table>
    <tr>
      <th>이름</th>
      <td><input type='text' name='name' required></td>
    </tr>
    <tr>
      <th>나이</th>
      <td><input type='number' name='age' required></td>
    </tr>
    <tr>
      <th>암호</th>
      <td><input type='password' name='password' required></td>
    </tr>
    <tr>
      <th>계급</th>
      <td>
        <select name='rank'>
          <option value='이병'>이병</option>
          <option value='일병'>일병</option>
          <option value='상병'>상병</option>
          <option value='병장'>병장</option>
        </select>
      </td>
    </tr>

    <tr>
      <th>사진</th>
      <td><input type='file' name='photofile'></td>
    </tr>

    <tr>
      <th>입대일</th>
      <td><input type='date' name='enlistmentDateStr' required></td>
    </tr>
  </table>
  <br>
  <a href='list' class='btn'>뒤로</a>
  <button class='btn'>등록</button>
  </form>
</div>
<jsp:include page="../footer.jsp"/>
</body>
</html>
