<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
   <body>
      <h1>Create\update the club form: </h1>
      <form action="club" method="post">
        <c:choose>
            <c:when test="${isCreate == true}">
                <p>Id : <input type="text" name="id" id="id" value="${club.id}"/></p>
            </c:when>
            <c:otherwise>
                <input type="hidden" name="id" value="${club.id}" />
            </c:otherwise>
        </c:choose>
        <p>Name : <input type="text" name="name" id="name" value="${club.name}"/></p>
        <p><input type="submit" value="Submit" /> <input type="reset" value="Reset" /> </p>
      </form>
   </body>
</html>