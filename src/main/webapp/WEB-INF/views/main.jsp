<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
    <c:if test="${user != null}">
        <h4>로그인됨</h4>
    </c:if>
    <h1>This is main page!!!!!!</h1>
</body>
</html>