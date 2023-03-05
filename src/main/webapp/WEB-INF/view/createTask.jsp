<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
   <body>
      <h1>Create a new task: </h1>
      <form action="<c:url value ='/menu/${user.id}/createPost'/>" method="post">
        <p>Caption : <input type="text" name="caption" id="name" value="${task.caption}"/></p>
        <p>Price : <input type="number" name="price" id="name" value="${task.price}"/></p>
        <p>Description : <input type="text" name="description" id="name" value="${task.description}"/></p>
        <p><input type="submit" value="Submit" /> <input type="reset" value="Reset" /> </p>
      </form>
   </body>
</html>