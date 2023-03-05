<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
   <body>
      <h1>Login with existing account: </h1>
      <form action="<c:url value ='/account/registerPost'/>" method="post">
        <p>Email : <input type="text" name="email" id="name" value="${register.email}"/></p>
        <p>Nickname: <input type="text" name="nickname" id="name" value="${register.nickName}"/></p>
        <p>Contact number : <input type="text" name="contactNumber" id="name" value="${register.contactNumber}"/></p>
        <p>Password : <input type="text" name="password" id="name" value="${register.password}"/></p>
        <p>Password confirmation: <input type="text" name="confirmPassword" id="name" value="${register.confirmPassword}"/></p>
        <p><input type="submit" value="Submit" /> <input type="reset" value="Reset" /> </p>
      </form>
   </body>
</html>