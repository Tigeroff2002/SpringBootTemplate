<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
   <body>
      <h1>Login with existing account: </h1>
      <form action="/api/account/loginPost" method="post">
        <p>Email : <input type="text" name="email" id="name" value="${user.email}"/></p>
        <p>Password : <input type="text" name="password" id="name" value="${user.password}"/></p>
        <p><input type="submit" value="Submit" /> <input type="reset" value="Reset" /> </p>
      </form>
   </body>
</html>