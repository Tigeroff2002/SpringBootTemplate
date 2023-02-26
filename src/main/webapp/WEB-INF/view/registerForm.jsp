<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
   <body>
      <h1>Login with existing account: </h1>
      <form action= "/api/account/registerPost" method="post">
        <p>Email : <input type="text" name="email" id="name" value="${user.Email}"/></p>
        <p>Nickname: <input type="text" name="nickname" id="name" value="${user.NickName}"/></p>
        <p>Contact number : <input type="text" name="contactNumber" id="name" value="${user.ContactNumber}"/></p>
        <p>Password : <input type="text" name="password" id="name" value="${user.Password}"/></p>
        <p>Password confirmation: <input type="text" name="confirmPassword" id="name" value="${user.ConfirmPassword}"/></p>
        <p><input type="submit" value="Submit" /> <input type="reset" value="Reset" /> </p>
      </form>
   </body>
</html>