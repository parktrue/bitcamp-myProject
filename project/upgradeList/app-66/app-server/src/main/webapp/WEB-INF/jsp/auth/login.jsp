<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"
        errorPage="/WEB-INF/jsp/error.jsp" %>
<%@ page import="bitcamp.myapp.vo.Soldier" %>

<%
  request.setAttribute("refresh", "2;url=/auth/form.jsp");

  Soldier s = new Soldier();
  s.setMilitaryNumber(request.getParameter("milNum"));
  s.setPassword(request.getParameter("password"));

  if (request.getParameter("saveMilNum") != null) {
    Cookie cookie = new Cookie("milNum", s.getMilitaryNumber());
    response.addCookie(cookie);
  } else {
    Cookie cookie = new Cookie("milNum", "no");
    cookie.setMaxAge(0);
    response.addCookie(cookie);
  }
%>

<jsp:useBean id="soldierDao" type="bitcamp.myapp.dao.SoldierDao" scope="application"/>

<%
  Soldier loginUser = soldierDao.findByMilnumAndPassword(s);
  if (loginUser == null) {
    throw new Exception("군인 정보가 일치하지 않습니다.");
  }

  session.setAttribute("loginUser", loginUser);
  response.sendRedirect("/");
%>
