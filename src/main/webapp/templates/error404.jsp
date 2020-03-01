<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 29.02.2020
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>PAGE NOT FOUND</title>
</head>
<body>
<h1>
    404
    <% out.print(exception.getLocalizedMessage()); %>
</h1>

</body>
</html>
