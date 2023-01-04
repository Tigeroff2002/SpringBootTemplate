<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "sf" uri = "http://www.springframework.org/tags/form" %>
<html>
   <body>
      <h1>Create\update the sportsman form: </h1>
      <sf:form action="sportsman" method="post" modelAttribute="sportsman">
        <p>Id : <sf:input path="id"/> <sf:errors path="id" cssClass="error"/> </p>
        <p>Name : <sf:input path="name"/> <sf:errors path="name" cssClass="error"/> </p>
        <p>Gender:
        Male <sf:radiobutton path="gender" value="Male"/>
        Female <sf:radiobutton path="gender" value="Female"/>
        </p>
        <p>Club:
        <sf:select path="clubId">
            <sf:options items="${clubList}" itemLabel="name" itemValue="id"/>
        </sf:select>
        </p>
        <p>Age : <sf:input path="age"/> <sf:errors path="name" cssClass="error"/> </p>
        <p><input type="submit" value="Submit" /> <input type="reset" value="Reset" /> </p>
      </sf:form>
   </body>
</html>