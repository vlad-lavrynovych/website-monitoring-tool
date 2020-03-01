<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 29.02.2020
  Time: 20:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>INTERNAL SERVER ERROR</title>
</head>
<body>
<h1>
    500
    <% out.print(exception.getLocalizedMessage()); %>
</h1>
</body>
</html>
