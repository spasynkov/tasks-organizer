<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><c:out value="${user.username}"/>'s profile</title>
</head>
<body>
<H2><c:out value="${user.username}"/>'s profile</H2>
<p>ID: <c:out value="${user.id}"/></p>
<p>Name: <c:out value="${user.username}"/></p>
<p>Admin privileges: <c:out value="${user.admin}"/></p>
<p><a href="<c:url value="/"/>">Back to main</a> | <a href="<c:url value="/login?logout"/>">Log out</a></p>
</body>
</html>
