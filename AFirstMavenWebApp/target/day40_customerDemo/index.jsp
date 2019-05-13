<%--
  Created by IntelliJ IDEA.
  User: L1455013965
  Date: 2019/5/12
  Time: 21:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="SaveCustomer" method="post">
    客户名称：<input type="text" name="name"/><br/>
    客户来源：<input type="text" name="source"/><br/>
    客户级别：<input type="text" name="level"/><br/>
    客户行业：<input type="text" name="industry"/><br/>
    客户地址：<input type="text" name="address"/><br/>
    客户电话：<input type="text" name="mobile"/><br/>
    <input type="submit" value="保存"/><br/>
</form>
</body>
</html>
