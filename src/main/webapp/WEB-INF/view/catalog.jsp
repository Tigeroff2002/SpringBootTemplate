<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<style type="text/css">
html {
  font-size: 14px;
  font-family: Georgia, serif;
}

@media (min-width: 768px) {
  html {
    font-size: 16px;
  }
}

html {
  position: relative;
  min-height: 100%;
  font-size: 14px;
}

body {
  margin-bottom: 60px;
  background-color: rgba(255, 255, 128, .5);
}

table {
    width: 100%;
}

td {
    border-spacing: 10px;
    border: 1px solid black;
}
.link {
    font-weight: bold;
    text-align: left;
    border: none;
    padding: 10px 15px;
    background: #d8d8d8;
    font-size: 14px;
}
.back{
    background: #00ffff;
}
.table {
    width: 100%;
    border: none;
    margin-bottom: 20px;
}

    .table thead th {
        font-weight: bold;
        text-align: left;
        border: none;
        padding: 10px 15px;
        background: #d8d8d8;
        font-size: 14px;
    }

    .table thead tr th:first-child {
        border-radius: 8px 0 0 8px;
    }

    .table thead tr th:last-child {
        border-radius: 0 8px 8px 0;
    }

    .table tbody td {
        text-align: left;
        border: none;
        padding: 10px 15px;
        font-size: 14px;
        vertical-align: top;
    }

    .table tbody tr:nth-child(even) {
        background: #f3f3f3;
    }

    .table tbody tr td:first-child {
        border-radius: 8px 0 0 8px;
    }

    .table tbody tr td:last-child {
        border-radius: 0 8px 8px 0;
    }

input {
    border: 1px solid #cccccc;
    border-radius: 5px;
    background: yellowgreen !important;
    outline: none;
    height: 24px;
    width: 120px;
    color: black;
    font-size: 11px;
    font-family: Tahoma;
}

input:focus {
        color: #000000;
        border: 1px solid #000000
}
</style>
</head>
<body>
<h3> Hello! </h3>
<br>
<h3> You are new user </h3>
<br>
<h3> <a href="<c:url value ='/account/login'/>"> Create a new task </a> </h3>
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
        <c:url value="/menu/details/task/${taskItem.id}" var="detailsURL" />
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