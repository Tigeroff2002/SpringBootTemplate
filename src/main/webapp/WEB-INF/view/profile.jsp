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
<h3> Hello, on account profile page! </h3>
<br>
<c:url value="/account/lk/${user.id}" var="lkURL" />
<h3> His nickname is ${user.email}</h3>
<br>
<h3> <a href="${lkURL}"> Go to your LK </a> </h3>
<br>
</body>
</html>