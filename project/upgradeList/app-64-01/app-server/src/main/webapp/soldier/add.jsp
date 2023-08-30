<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"%>
<%@ page import="bitcamp.myapp.vo.Soldier" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeParseException" %>

<jsp:useBean id="soldierDao" type="bitcamp.myapp.dao.SoldierDao" scope="application"/>
<jsp:useBean id="sqlSessionFactory" type="org.apache.ibatis.session.SqlSessionFactory" scope="application"/>
<jsp:useBean id="ncpObjectStorageService" type="bitcamp.myapp.service.NcpObjectStorageService" scope="application"/>

<%
  Soldier loginUser = (Soldier) request.getSession().getAttribute("loginUser");
  if (loginUser == null || !loginUser.getName().equals("행정관")) {
    response.sendRedirect("/auth/login.jsp");
    return;
  }

  Soldier s = new Soldier();
  s.setName(request.getParameter("name"));
  s.setRank(request.getParameter("rank"));

  String ageStr = request.getParameter("age");
  if (ageStr != null && !ageStr.isEmpty()) {
    s.setAge(Integer.parseInt(ageStr));
  }
  s.setPassword(request.getParameter("password"));

  String enlistmentDateStr = request.getParameter("enlistmentDate");
  if (enlistmentDateStr != null && !enlistmentDateStr.isEmpty()) {
    try {
      LocalDate enlistmentDate = LocalDate.parse(enlistmentDateStr);
      s.setEnlistmentDate(enlistmentDate);
    } catch (DateTimeParseException e) {
      e.printStackTrace();
    }
  }

  Part photoPart = request.getPart("photo");
  if (photoPart.getSize() > 0) {
    String uploadFileUrl = ncpObjectStorageService.uploadFile(
            "bitcamp-nc7-bucket-01", "soldier/", photoPart);
    s.setPhoto(uploadFileUrl);
  }

  soldierDao.insert(s);
  sqlSessionFactory.openSession(false).commit();
  response.sendRedirect("list.jsp");
%>

