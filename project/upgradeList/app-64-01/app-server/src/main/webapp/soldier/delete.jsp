<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"
        errorPage="/error.jsp" %>
<%@ page import="bitcamp.myapp.vo.Soldier" %>
<jsp:useBean id="soldierDao" type="bitcamp.myapp.dao.SoldierDao" scope="application"/>
<jsp:useBean id="sqlSessionFactory" type="org.apache.ibatis.session.SqlSessionFactory" scope="application"/>

<%
  Soldier loginUser = (Soldier) request.getSession().getAttribute("loginUser");
  if (loginUser == null || !loginUser.getName().equals("행정관")) {
    response.sendRedirect("/auth/login.jsp");
    return;
  }
  request.setAttribute("refresh", "2;url=list.jsp");

  if (soldierDao.delete(Integer.parseInt(request.getParameter("no"))) == 0) {
    throw new Exception("해당 번호의 군인이 없습니다.");
  } else {
    sqlSessionFactory.openSession(false).commit();
    response.sendRedirect("/soldier/list.jsp");
  }
%>

