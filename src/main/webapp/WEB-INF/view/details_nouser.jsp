<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<h3> <a href="<c:url value ='/account/register'/>"> Sign up </a> </h3>
<h3> <a href="<c:url value ='/account/login'/>"> Sign in </a> </h3>
<br>
<c:url value="/" var="indexURL" />
<h3> Details of task: </h3>
<h4> "${task.caption}" </h4>
<h4> "${task.type}" </h4>
<h4> "${task.price}" </h4>
<h4> "${task.description}" </h4>
<h4> "${task.executorId}" </h4>
<br>
<h3> <a href="${indexURL}"> Back to index </a> </h3>
</body>
</html>