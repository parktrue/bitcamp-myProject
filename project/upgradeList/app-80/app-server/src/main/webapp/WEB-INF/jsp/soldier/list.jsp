<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset='UTF-8'>
  <title>인사정보체계</title>
<jsp:include page="../header.jsp"/>
  <link rel="stylesheet" href="../../../css/soldier/list.css">
</head>
<body>
<div class='container'>
  <h1>병력 현황</h1>
  <a href='form' class='btn'>전입신병 등록</a>
  <a href='/' class='btn'>메인 화면으로</a>
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
        <a href=${soldier.no}>${soldier.name}</a></td>
      <td>${soldier.rank}</td>
      <td>${soldier.age}</td>
      <td>${soldier.enlistmentDate}</td>
      <td>${soldier.dischargeDate}</td>
      <td>${soldier.dDay}
      </td>
      <td><a href='detail?no=${soldier.no}'>상세</a> | <a href='delete?no=${soldier.no}'>삭제</a></td>
    </tr>
      </c:forEach>
    </tbody>
  </table>
</div>
</body>
<jsp:include page="../footer.jsp"/>
</html>
