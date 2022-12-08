<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
   <head></head>
   <body>
      <h1>Clubs:</h1> <br/>
      <h3> <a href="<c:url value='/club'/>">Create a new club: </a> </h3> <br/>
    <table border="2">
        <tr>
            <td>id</td>
            <td>name</td>
            <td>remove</td>
            <td>update</td>
        </tr>
        <c:forEach items="${clubList}" var="clubItem">
            <c:url value="/club/${clubItem.id}" var="deleteURL" />
            <c:url value="/club" var="updateURL">
                <c:param name="id" value="${clubItem.id}" />
            </c:url>
        <tr>
            <td><c:out value="${clubItem.id}"/> </td>
            <td><c:out value="${clubItem.name}"/> </td>
            <td><a href = "${deleteURL}"> delete </a></td>
            <td><a href = "${updateURL}"> update </a></td>
        </tr>
        </c:forEach>
    </table>
   </body>
</html>