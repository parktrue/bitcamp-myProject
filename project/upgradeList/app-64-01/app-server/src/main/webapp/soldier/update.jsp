<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"
%>
<%@ page import="bitcamp.myapp.vo.Soldier" %>

<jsp:useBean id="soldierDao" type="bitcamp.myapp.dao.SoldierDao" scope="application"/>
<jsp:useBean id="sqlSessionFactory" type="org.apache.ibatis.session.SqlSessionFactory" scope="application"/>
<jsp:useBean id="ncpObjectStorageService" type="bitcamp.util.NcpObjectStorageService" scope="application"/>

<%
  request.setAttribute("refresh", "2;url=list.jsp");
  Soldier loginUser = (Soldier) request.getSession().getAttribute("loginUser");
  if (loginUser == null) {
    response.sendRedirect("/auth/form.jsp");
  }

  Soldier soldier = new Soldier();
  soldier.setNo(Integer.parseInt(request.getParameter("no")));
  soldier.setName(request.getParameter("name"));
  soldier.setRank(request.getParameter("rank"));
  soldier.setAge(Integer.parseInt(request.getParameter("age")));
  soldier.setMilitaryNumber(request.getParameter("militaryNumber"));

  Part photoPart = request.getPart("photo");
  if (photoPart.getSize() > 0) {
    String uploadFileUrl = ncpObjectStorageService.uploadFile("bitcamp-nc7-bucket-01", "soldier/", photoPart);
    soldier.setPhoto(uploadFileUrl);
  }
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset='UTF-8'>
  <meta http-equiv='refresh' content='1;url=/soldier/list.jsp'>
  <title>병사</title>
</head>
<body>
<jsp:include page="../header.jsp"/>
<link rel="stylesheet" href="../header.css">
<h1>병사 변경</h1>
<% try {
  if (soldierDao.update(soldier) == 0) {%>
<p>해당 병사가 없습니다.</p>
<% } else {
  sqlSessionFactory.openSession(false).commit();%>
<p>변경했습니다!</p>
<%
  }
} catch (Exception e) {
%>
<%
  sqlSessionFactory.openSession(false).rollback();
%>
<p>변경 실패입니다!</p>
<%
    e.printStackTrace();
  }
%>
<jsp:include page="../footer.jsp"/>
<link rel="stylesheet" href="../footer.css">
</body>
</html>
