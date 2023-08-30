<%@ page
language="java"
pageEncoding="UTF-8"
contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>국군인트라넷</title>
<link rel="stylesheet" href="../../../css/auth/form.css">
</head>
<body>
<jsp:include page="../header.jsp"/>
<link rel="stylesheet" href="../../../css/header.css">
    <div class="container">
        <img src="/marinedog.png" alt="병력 관리" class="logo">
        <h1>로그인</h1>
        <form action='login' method="post">
            <table border="1">
                <tr>
                    <th>군번</th>
                    <td><input type='text' name='milNum' value="${cookie.milNum.value}" placeholder="군번을 입력하세요."></td>
                </tr>
                <tr>
                    <th>암호</th>
                    <td><input type='password' name='password' placeholder="암호를 입력하세요."></td>
                </tr>
            </table>
            <br>
            <button class="btn">로그인</button>
            <a href="/" class="btn">뒤로</a>
            <input type="checkbox" name="saveMilNum" ${cookie.milNum != null ? "checked" : ""}> 군번저장
        </form>
    </div>
            <jsp:include page="../footer.jsp"/>
            <link rel="stylesheet" href="../../../css/footer.css">
</body>
</html>
