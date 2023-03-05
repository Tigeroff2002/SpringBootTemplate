<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<c:url value="/account/index/${user.id}" var="indexURL" />
<c:url value="/account/lk/admin/${user.id}" var="lkAdminURL" />
<h3> Hello in your LK ${user.email} ! </h3>
<h3> <a href="${indexURL}"> Back to index </a> </h3>
<br>
<h3> <a href="${lkAdminURL}"> Go to your admin page </a> </h3>
<br>
<br>
<h3> <a href="/SpringMVC/account/logout"> Logout </a> </h3>
<br>
<h3> You are auth user! Its your LK! </h3>
<br>
<h4> "${user.nickName}" </h4>
<h4> "${user.email}" </h4>
<h4> "${user.contactNumber}" </h4>
<h4> "${user.roleId}" </h4>
</body>
</html>