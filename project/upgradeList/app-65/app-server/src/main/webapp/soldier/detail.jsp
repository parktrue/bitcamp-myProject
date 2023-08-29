<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset='UTF-8'>
  <title>병력 상세 정보</title>
  <link rel="stylesheet" href="detail.css">
</head>
<body>
<jsp:include page="../header.jsp"/>
<link rel="stylesheet" href="../header.css">
<div class='container'>

  <h1>병사 상세 정보</h1>
<c:if test="${empty soldier}">
  <p>해당 번호의 병사가 없습니다!</p>
</c:if>

<c:if test="${not empty soldier}">
  <form action='/soldier/update' method='post' enctype='multipart/form-data'>
    <table border='1'>
      <tr>
        <th style='width:120px;'>사진</th>
        <td style='width:300px;'>
          ${soldier.photo == null ?
                  "<img style='height:80px' src='/images/avatar.png'>" :
                  "<a href='https://kr.object.ncloudstorage.com/bitcamp-nc7-bucket-01/soldier/"+=soldier.photo+="'>"+=
                          "<img src='http://xxqrmvmzbxkt19010716.cdn.ntruss.com/soldier/"+=soldier.photo+=
                          "?type=f&w=60&h=80&faceopt=true&ttype=jpg'>"+="</a>"
                  }
          <input type='file' name='photo'></td>
      </tr>
      <tr>
        <th style='width:120px;'>번호</th>
        <td style='width:300px;'><input type='text' name='no' value=${soldier.no} readonly></td>
      </tr>
      <tr>
        <th>이름</th>
        <td><input type='text' name='name' value=${soldier.name}></td>
      </tr>
      <tr>
        <th>군번</th>
        <td><input type='text' name='militaryNumber' value=${soldier.militaryNumber}></td>
      </tr>

      <tr>
        <th>나이</th>
        <td><input type='text' name='age' value=${soldier.age}></td>
      </tr>
      <tr>
        <th>암호</th>
        <td><input type='password' name='password'></td>
      </tr>
      <tr>
        <th>입대일</th>
        <td><input type='date' name='enlistmentDate' value=${soldier.enlistmentDate}></td>
      </tr>
      <tr>
        <th>전역일</th>
        <td><input type='date' name='dischargeDate' value=${soldier.dischargeDate}></td>
      </tr>
      <tr>
        <th>D-Day</th>
        <td>${soldier.dDay}</td>
      </tr>

      <tr>
        <th>계급</th>
        <td><select name='rank'>
          <option value='이병' ${soldier.rank eq "이병"  ? "selected" : ""}>이병</option>
          <option value='일병' ${soldier.rank eq "일병"  ? "selected" : ""}>일병</option>
          <option value='상병' ${soldier.rank eq "상병"  ? "selected" : ""}>상병</option>
          <option value='병장' ${soldier.rank eq "병장"  ? "selected" : ""}>병장</option>
        </select></td>
      </tr>
    </table>

    <div>
      <button class='btn'>변경</button>
      <button class='btn' type='reset'>초기화</button>
      <a href='/soldier/delete?no=${param.no}' class='btn'>삭제</a>
      <a href='list' class='btn'>목록</a>
    </div>
  </form>
</c:if>

</div>
<jsp:include page="../footer.jsp"/>
<link rel="stylesheet" href="../footer.css">
</body>
</html>
