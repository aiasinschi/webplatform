<%--
  Created by IntelliJ IDEA.
  User: Adrian
  Date: 1/2/2015
  Time: 5:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%@ include file="includes/header.jsp" %>

<section class="esta-content">
  <div class="esta-register">
    Enter your registration details<br/><br/>
    <form action = "register" method="post">
      Choose username: <input type = "text" name = "username" value = "${username}" /></br>
      Choose password: <input type = "password" name = "password" /></br>
      Repeat password: <input type = "password" name = "repeatpassword" /></br>
      Enter full name: <input type = "text" name = "fullname" value = "${fullname}" /></br>
      Enter e-mail: <input type = "text" name = "email" value = "${email}" /></br></br>
      <input type=submit value="Register" name="register">
    </form>
    <br/> <br/>
    <p class="esta-error">${registrationErrors}</p>
  </div>
</section>

<%@ include file="includes/footer.jsp" %>
