<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>login</title>
</head>
<body>
    <h1>로그인</h1>
    <form action="/loginprocess" method="POST">
        아이디 : <input name="userid" /><br/>
        비밀번호 : <input name="userpw"><br/>
        <button type="submit">로그인</button>
    </form>
</body>
</html>