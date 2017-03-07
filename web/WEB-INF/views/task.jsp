<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Task #<c:out value="${task.id}"/></title>
</head>
<body>
<H3>Task #<c:out value="${task.id}"/></H3>
<p>Description: <c:out value="${task.description}"/></p>
<p>Status: <c:set var="status" value="${fn:replace(task.status.name().toLowerCase(), '_', ' ')}"/>
    <c:out value="${status}"/></p>
<p>Created by: <c:out value="${task.author.username}"/></p>
<p>Created at: <c:out value="${task.timestampOfCreation}"/></p>
<c:if test="${task.performer != null}">
    <p>Taken by: <c:out value="${task.performer.username}"/></p>
</c:if>
<c:if test="${task.timestampOfClose != null}">
    <p>Expire time: <c:out value="${task.timestampOfClose}"/></p>
</c:if>
</body>
</html>
