<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<h3> <a href="/SpringMVC/api/account/logout"> Logout </a> </h3>
<br>
<h3> Hello, on account index page! </h3>
<br>
<c:url value="/api/account/lk/${user.id}" var="lkURL" />
<h3> Your nick is ${user.email}</h3>
<br>
<h3> <a href="${lkURL}"> Go to your LK </a> </h3>
<br>
</body>
</html>