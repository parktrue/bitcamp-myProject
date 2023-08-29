<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="soldierDao" type="bitcamp.myapp.dao.SoldierDao" scope="application"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset='UTF-8'>
  <title>인사정보체계</title>
  <link rel="stylesheet" href="list.css">
</head>
<body>
<jsp:include page="../header.jsp"/>
<link rel="stylesheet" href="../header.css">
<div class='container'>
  <h1>병력 현황</h1>
  <a href='form.jsp' class='btn'>전입신병 등록</a>
  <a href='/index.jsp' class='btn'>메인 화면으로</a>
  <img src='/marinedog2.png' alt='설명' style='margin-left: 20px'/>
  <br><br>
  <table border='1'>
    <thead>
    <tr>
      <th>번호</th>
      <th>이름</th>
      <th>계급</th>
      <th>나이</th>
      <th>입대일</th>
      <th>전역일</th>
      <th>D-Day</th>
      <th>동작</th>
    </tr>
    </thead>
      <c:forEach items="${list}" var="soldier">
    <tr>
      <td>${soldier.no}</td>
      <td>
        <img src='http://xxqrmvmzbxkt19010716.cdn.ntruss.com/soldier/${soldier.photo}?type=f&w=15&h=15&faceopt=true&ttype=jpg'>
        <a href='/soldier/detail?no=${soldier.no}'>${soldier.name}</a></td>
      <td>${soldier.rank}</td>
      <td>${soldier.age}</td>
      <td>${soldier.enlistmentDate}</td>
      <td>${soldier.dischargeDate}</td>
      <td>${soldier.dDay}
      </td>
      <td><a href='detail?no=${soldier.no}'>상세</a> | <a href='/soldier/delete?no=${soldier.no}'>삭제</a></td>
    </tr>
      </c:forEach>
    </tbody>
  </table>
</div>
<jsp:include page="../footer.jsp"/>
<link rel="stylesheet" href="../footer.css">
</body>
</html>
