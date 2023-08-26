<%@ page
language="java"
pageEncoding="UTF-8"
contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>비트캠프</title>
<link rel="stylesheet" href="form.css">
</head>
<body>
<jsp:include page="../header.jsp"/>
<link rel="stylesheet" href="../header.css">
    <div class="container">
        <img src="/marinedog.png" alt="병력 관리" class="logo">
        <h1>로그인</h1>
        <form action='/auth/login' method="post">
            <table border="1">
                <tr>
                    <th>군번</th>
                    <td><input type='text' name='milNum'></td>
                </tr>
                <tr>
                    <th>암호</th>
                    <td><input type='password' name='password'></td>
                </tr>
            </table>
            <br>
            <button class="btn">로그인</button>
            <a href="/index.jsp" class="btn">뒤로</a>
        </form>
    </div>
            <jsp:include page="../footer.jsp"/>
            <link rel="stylesheet" href="../footer.css">
</body>
</html>
