<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<c:url value="/account/lk/${user.id}" var="lkURL" />
<c:url value="/account/index/${user.id}" var="indexURL" />
<h3> <a href="${indexURL}"> Back to index </a> </h3>
<br>
<h3> Hello, on admin Page! </h3>
<h3> <a href="${lkURL}"> Back to your LK </a> </h3>
<br>
<br>
<h3> <a href="<c:url value ='/account/logout'/>"> Logout </a> </h3>
<br>
<h3> You are admin! </h3>
<br>
<h3> Admin Info ....</h3>
<br>
</body>
</html>