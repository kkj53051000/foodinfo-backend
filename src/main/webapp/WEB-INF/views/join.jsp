<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>join</title>
</head>
<body>
    <h1>가입 페이지</h1>
    <form action="/joinprocess" method="POST">
        아이디 : <input name="userid" /><br />
        비밀번호 : <input name="userpw" type="password" /><br />
        닉네임 : <input name="nickname" /><br />
        이메일 : <input name="email" /><br />
        <button type="submit">가입</button>
    </form>
</body>
</html>