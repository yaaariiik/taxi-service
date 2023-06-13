<%@ page contentType="text/html;charset=UTF-8" %>
<style>
    <%@include file='/WEB-INF/views/css/table_dark.css' %>
</style>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form method="post" id="login" action="${pageContext.request.contextPath}/login"></form>
<h1 class="table_dark">Login Page</h1>
<table class="table_dark">
    <tr>
        <th><h4 style="color:red">${errorMsg}</h4></th>
    </tr>
</table>
<table border="1" class="table_dark">
    <tr>
        <th>Enter login</th>
        <th>Enter password</th>
        <th>Submit</th>
    </tr>
    <tr>
        <td>
            <input type="text" name="login" form="login" required>
        </td>
        <td>
            <input type="password" name="password" form="login" required>
        </td>
        <td>
            <input type="submit" name="add" form="login">
        </td>
    </tr>
</table>
<table class="table_dark" >
    <tr>
        <th><a href="${pageContext.request.contextPath}/drivers/add">Register</a></th>
    </tr>
</table>
</body>
</html>
