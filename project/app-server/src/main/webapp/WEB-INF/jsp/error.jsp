<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  if (request.getAttribute("refresh") != null) {
    response.setHeader("Refresh", (String) request.getAttribute("refresh"));
  }
%>
<!DOCTYPE html>
<html>
<link rel="stylesheet" href="/css/error.css">
<head>
  <meta charset='UTF-8'>
  <title>실행오류</title>
</head>
<body>

<jsp:include page="header.jsp"/>

<h1>실행 오류!</h1>

<c:if test="${message}">
  <p>${message}</p>
</c:if>

<hr>
<p><%=exception%></p>


<jsp:include page="footer.jsp"/>

</body>
</html>











