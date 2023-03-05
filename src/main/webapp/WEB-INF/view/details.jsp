<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<h3> <a href="<c:url value ='/account/logout'/>"> Logout </a> </h3>
<br>
<c:url value="/account/index/${user.id}" var="indexURL" />
<h3> <a href="${indexURL}"> Back to index </a> </h3>
<br>
<h3> <a href="<c:url value ='/menu/${user.id}/room/task/${task.id}'/>"> Go to task room page </a> </h3>
<br>
<h3> Details of task: </h3>
<h4> "${task.caption}" </h4>
<h4> "${task.type}" </h4>
<h4> "${task.price}" </h4>
<h4> "${task.description}" </h4>
<h4> "${task.executorId}" </h4>
</body>
</html>