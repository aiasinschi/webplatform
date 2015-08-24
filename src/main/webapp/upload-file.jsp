<%--
  Created by IntelliJ IDEA.
  User: Adrian
  Date: 1/6/2015
  Time: 8:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header_logged.jsp" %>

<section class="esta-content">

    <s:form action="excel-import" namespace="/"
            method="POST" enctype="multipart/form-data">

        <s:file name="fileUpload" label="Select a File to upload" size="40"/>

        <s:submit value="submit" name="submit"/>

    </s:form>

</section>
<%@ include file="includes/footer.jsp" %>