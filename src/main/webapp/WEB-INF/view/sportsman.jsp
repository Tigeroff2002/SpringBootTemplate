<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
   <head></head>
   <body>
      <h1>Sportsmen:</h1> <br/>
      <h3> <a href="<c:url value='/sportsman'/>">Create a new sportsman: </a> </h3> <br/>
    <table border="2">
        <tr>
            <td>id</td>
            <td>name</td>
            <td>gender</td>
            <td>club</td>
            <td>remove</td>
            <td>update</td>
        </tr>
        <c:forEach items="${sportsmanList}" var="sportsmanItem">
            <c:url value="/club/${sportsmanItem.id}" var="deleteURL" />
            <c:url value="/club" var="updateURL">
                <c:param name="id" value="${clubItem.id}" />
            </c:url>
        <tr>
            <td><c:out value="${sportsmanItem.id}"/> </td>
            <td><c:out value="${sportsmanItem.name}"/> </td>
            <td><c:out value="${sportsmanItem.gender}"/> </td>
            <td><c:out value="${sportsmanItem.clubId}"/> </td>
            <td><!--a href = "${deleteURL}"> delete </a--></td>
            <td><!--a href = "${updateURL}"> update </a--></td>
        </tr>
        </c:forEach>
    </table>
   </body>
</html>