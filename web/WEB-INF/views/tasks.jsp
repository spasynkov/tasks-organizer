<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tasks</title>
</head>
<body>
<H3>Tasks:</H3>
<c:forEach items="${tasksList}" var="task">
    <ul>
        <li><a href="/tasks/${task.id}"><c:out value="${task.description}"/></a></li>
    </ul>
</c:forEach>
</body>
</html>
