<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="bitcamp.myapp.vo.Soldier" %>
<%@ page import="java.time.temporal.ChronoUnit" %>
<%@ page import="java.time.LocalDate" %>

<jsp:useBean id="soldierDao" type="bitcamp.myapp.dao.SoldierDao" scope="application"/>
<%
  Soldier loginUser = (Soldier) request.getSession().getAttribute("loginUser");
  if (loginUser == null) {
    response.sendRedirect("/auth/login.jsp");
    return;
  }
  List<Soldier> soldiers = soldierDao.findAll();
  soldierDao.updateDday();
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset='UTF-8'>
  <title>병사 목록</title>
  <link rel="stylesheet" href="list.css">
</head>
<body>
<jsp:include page="../header.jsp"/>
<link rel="stylesheet" href="../header.css">
<div class='container'>
  <h1>병사 목록</h1>
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
    <%
      for (Soldier soldier : soldiers) {
        pageContext.setAttribute("soldier", soldier);
        long dDay = ChronoUnit.DAYS.between(LocalDate.now(), soldier.getDischargeDate());
    %>
    <tr>
      <td>${soldier.no}</td>
      <td>
        <img src='http://xxqrmvmzbxkt19010716.cdn.ntruss.com/soldier/${soldier.photo}?type=f&w=15&h=15&faceopt=true&ttype=jpg'>
        <a href='/soldier/detail.jsp?no=${soldier.no}'>${soldier.name}</a></td>
      <td>${soldier.rank}</td>
      <td>${soldier.age}</td>
      <td>${soldier.enlistmentDate}</td>
      <td>${soldier.dischargeDate}</td>
      <td><%=dDay%>
      </td>
      <td><a href='detail.jsp?no=${soldier.no}'>상세</a> | <a href='/soldier/delete.jsp?no=${soldier.no}'>삭제</a></td>
    </tr>
    <%}%>
    </tbody>
  </table>
</div>
<jsp:include page="../footer.jsp"/>
<link rel="stylesheet" href="../footer.css">
</body>
</html>
