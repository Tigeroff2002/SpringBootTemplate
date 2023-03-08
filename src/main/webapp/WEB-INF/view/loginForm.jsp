<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
      <h1>Login with existing account: </h1>
      <form action="<c:url value ='/account/loginPost'/>" method="post">
        <p>Email : <input type="text" name="email" id="name" value="${login.email}"/></p>
        <p>Password : <input type="text" name="password" id="name" value="${login.password}"/></p>
        <p><input type="submit" value="Submit" /> <input type="reset" value="Reset" /> </p>
      </form>
<h3> <a href="<c:url value ='/account/register'/>"> Don`t have an account? </a> </h3>
<br>
<c:url value="/" var="indexURL" />
<h3> <a href="${indexURL}"> Back to index </a> </h3>
<br>
</body>
</html>