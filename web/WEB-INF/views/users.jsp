<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<H3>Users:</H3>
<ul>
    <c:forEach items="${users}" var="username">
        <li><a href="/users/<c:out value="${username}" />"><c:out value="${username}"/></a></li>
    </c:forEach>
</ul>
</body>
</html>
