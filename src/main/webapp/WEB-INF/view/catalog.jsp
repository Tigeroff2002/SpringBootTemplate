<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<h3> Hello! </h3>
<br>
<h3> You are new user </h3>
<br>
<h3> <a href="<c:url value ='/menu/${user.id}/create'/>"> Create a new task </a> </h3>
<br>
<h3> <a href="<c:url value ='/account/register'/>"> Sign up </a> </h3>
<br>
<h3> <a href="<c:url value ='/account/login'/>"> Sign in </a> </h3>
<br>
<h3> List of tasks: </h3>
    <table border="2">
        <tr>
            <td>id</td>
            <td>caption</td>
            <td>price</td>
            <td>description</td>
            <td>executorId</td>
            <td>task details</td>
        </tr>
        <c:forEach items="${taskList}" var="taskItem">
        <c:url value="/menu/${user.id}/details/task/${taskItem.id}" var="detailsURL" />
        <tr>
            <td><c:out value="${taskItem.id}"/> </td>
            <td><c:out value="${taskItem.caption}"/> </td>
            <td><c:out value="${taskItem.price}"/> </td>
            <td><c:out value="${taskItem.description}"/> </td>
            <td><c:out value="${taskItem.executorId}"/> </td>
            <td><a href = "${detailsURL}"> details </a></td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>