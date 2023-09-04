<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        isErrorPage="true"%>
<jsp:useBean id="sqlSessionFactory" type="org.apache.ibatis.session.SqlSessionFactory" scope="application"/>
<%
  sqlSessionFactory.openSession(false).rollback();
  if (request.getAttribute("refresh") != null) {
    response.setHeader("Refresh", (String) request.getAttribute("refresh"));
  }
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset='UTF-8'>
  <title>실행오류</title>
</head>
<body>

<jsp:include page="header.jsp"/>
<link rel="stylesheet" href="../../css/header.css">
<h1>실행 오류!</h1>
<c:if test="${message}">
  <p>${message}</p>
</c:if>
<hr>
<p><%=exception%></p>

<jsp:include page="footer.jsp"/>
<link rel="stylesheet" href="../../css/footer.css">
</body>
</html>